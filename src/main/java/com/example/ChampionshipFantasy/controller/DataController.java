package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.*;
import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.model.enums.LineUpType;
import com.example.ChampionshipFantasy.repository.*;
import com.example.ChampionshipFantasy.service.FantasyTeamService;
import com.example.ChampionshipFantasy.service.PlayerGameweekService;
import com.example.ChampionshipFantasy.service.PlayerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.beans.EventSetDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static com.example.ChampionshipFantasy.model.enums.EventType.*;
import static java.util.Comparator.comparing;

@RestController
@RequestMapping("/data")
public class DataController {

    private static final String URLBASE = "https://soccer.sportmonks.com/api/v2.0/";
    private static final String APITOKEN = "api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J";

    private TeamRepository teamRepository;
    private GameweekRepository gameweekRepository;
    private PlayerRepository playerRepository;
    private FixtureRepository fixtureRepository;
    private PlayerGameweekRepository playerGameweekRepository;
    private FantasyTeamService fantasyTeamService;
    private PlayerService playerService;
    private PlayerGameweekService playerGameweekService;

    @Autowired
    public DataController(TeamRepository teamRepository, GameweekRepository gameweekRepository, PlayerRepository playerRepository,
                          FixtureRepository fixtureRepository, PlayerGameweekRepository playerGameweekRepository,
                          FantasyTeamService fantasyTeamService, PlayerService playerService,
                          PlayerGameweekService playerGameweekService) {
        this.teamRepository = teamRepository;
        this.gameweekRepository = gameweekRepository;
        this.playerRepository = playerRepository;
        this.fixtureRepository = fixtureRepository;
        this.playerGameweekRepository = playerGameweekRepository;
        this.fantasyTeamService = fantasyTeamService;
        this.playerService = playerService;
        this.playerGameweekService = playerGameweekService;
    }

    @PostMapping("/loaddata")
    public void load() {
        loadTeamsandPlayers();
        loadGameweeksAndFixtures();
        createAllPlayerGameweeks();
    }

    @PostMapping("/loadlive")
    public void loadlive() {
        loadLive();
    }

    //should be done automatcially, not activited from rest
    //Look into creating one directional relationship between fntteamgmw and selections
    //create a getgameweek method to get the current gameweek, instead of using fantasyteam or selection objects
    @PostMapping("/startGameweek")
    public void startGamweek() {
        fantasyTeamService.createNextGameweekForEachTeam();
    }

//    @PostMapping("/endGameweek")
//    public void endGameweekAndPrepareNext() {
//        fantasyTeamService.updateTotalScores();
//    }

    @PostMapping("/createGw")
    public void aye() {
        createAllPlayerGameweeks();
    }

    private void loadtest() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(new File("src\\main\\resources\\team.json"));
            List<Team> teams = Arrays.asList(mapper.readValue(node.get("data").traverse(), Team[].class));
            teamRepository.saveAll(teams);
            System.out.println("yes");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //API endpoint returns the teams with the players nested within them and persists both
    private void loadTeamsandPlayers() {
        ObjectMapper mapper = new ObjectMapper();
        try {

            String s = callURL(URLBASE + "teams/season/17141?include=squad,squad.player,squad.player.position&"
                    + APITOKEN);
            JsonNode node = mapper.readTree(s);
            List<Team> teams = Arrays.asList(mapper.readValue(node.get("data").traverse(), Team[].class));
            teamRepository.saveAll(teams);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadGameweeksAndFixtures() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = callURL(URLBASE + "seasons/17141?include=rounds.fixtures&" + APITOKEN);
            JsonNode node = mapper.readTree(s);

            List<Gameweek> gameweeks = Arrays.asList(mapper.readValue(node.get("data").get("rounds").get("data").traverse(), Gameweek[].class));
            gameweekRepository.saveAll(gameweeks);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //still need to get the subbed on and off ids
    private void loadLive() {
        for (Gameweek gameweek : gameweekRepository.findAll()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String s = callURL(URLBASE + "rounds/" + gameweek.getId() +
                        "?include=fixtures.events,fixtures.lineup,fixtures.bench&" + APITOKEN);
                JsonNode node = mapper.readTree(s);
                List<Fixture> fixtures = Arrays.asList(mapper.readValue(node.get("data")
                        .findValue("fixtures").get("data").traverse(), Fixture[].class));

                for (Fixture fixture : fixtures) {
                    List<PlayerGameweek> playerGameweeks = fixture.getPlayerGameweeks();
                    awardBonusPoints(playerGameweeks);

                    long homeTeamId = fixture.getHomeTeam().getId();
                    long awayTeamId = fixture.getAwayTeam().getId();

                    List<Event> homeTeamGoalsConceded = getGoalsConcededForTeam(fixture.getEvents(), homeTeamId);
                    List<Event> awayTeamGoalsConceded = getGoalsConcededForTeam(fixture.getEvents(), awayTeamId);

                    for (PlayerGameweek pg : playerGameweeks) {
                        long playerId = pg.getPlayer().getId();
                        if (!playerRepository.existsById(playerId)) {
                            playerService.getPlayerandSave(playerId);
                        }

                        checkAndSetSubbedOffandOnIds(fixture.getEvents(), pg);

                        if (pg.getTeam().getId() == homeTeamId)
                            pg.setPoints(playerGameweekService.calculateTotalPoints(pg, homeTeamGoalsConceded));
                        else pg.setPoints(playerGameweekService.calculateTotalPoints(pg, awayTeamGoalsConceded));

                        playerGameweekRepository.save(pg);
                    }
                }
                fixtureRepository.saveAll(fixtures);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private void awardBonusPoints(List<PlayerGameweek> playerGameweeks) {
        playerGameweeks.sort((o1, o2) -> Double.compare(o2.getRating(), o1.getRating()));
        int bonusPoints = 3;
        for (int i = 0; i < 3; i++) {
            playerGameweeks.get(i).setBonus(bonusPoints);
            bonusPoints--;
        }
    }

    //should probably be in a team service class
    private List<Event> getGoalsConcededForTeam(List<Event> events, long teamId) {
        List<Event> goals = new ArrayList<>();
        for (Event event : events) {
            if (event.getTeam().getId() != teamId) {
                switch (event.getType()) {
                    case GOAL:
                    case PENALTY:
                        //double check team id for OG
                    case OWNGOAL:
                        goals.add(event);
                        break;
                    default:
                }
            }
        }
        return goals;
    }

    private void checkAndSetSubbedOffandOnIds(List<Event> events, PlayerGameweek pg) {
        for (Event event : events) {
            if (event.getType() == SUBSTITUTION) {
                if (event.getPlayer().getId().equals(pg.getPlayer().getId())) {
                    pg.setSubbedOnEventId(event.getId());
                } else if (event.getRelatedPlayer().getId().equals(pg.getPlayer().getId())) {
                    pg.setSubbedOffEventId(event.getId());
                }
            }
        }
    }

    private void createAllPlayerGameweeks() {
        Gameweek gameweek = gameweekRepository.findById(194993L).orElse(null);
        for (Fixture fixture : gameweek.getFixtures()) {
            for (Player player : playerRepository.findAll()) {
                PlayerGameweek playerGameweek = new PlayerGameweek(player, fixture);
                playerGameweekRepository.save(playerGameweek);
            }
        }
    }

    // work out what class to put this in
    private String callURL(String url1) throws IOException {
        String contentString;
        URL url = new URL(url1);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //con.setRequestProperty("X-Auth-Token", "bc2a33c0a22244ce83c272a2e9562655");
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        contentString = content.toString();

        return contentString;
    }
}
