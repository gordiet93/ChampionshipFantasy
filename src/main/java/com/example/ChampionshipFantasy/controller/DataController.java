package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.*;
import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.model.enums.LineUpType;
import com.example.ChampionshipFantasy.repository.*;
import com.example.ChampionshipFantasy.service.FantasyTeamService;
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

import static java.util.Comparator.comparing;

@RestController
@RequestMapping("/data")
public class DataController {

    private TeamRepository teamRepository;
    private GameweekRepository gameweekRepository;
    private PlayerRepository playerRepository;
    private FixtureRepository fixtureRepository;
    private PlayerGameweekRepository playerGameweekRepository;
    private FantasyTeamService fantasyTeamService;
    private PlayerService playerService;

    @Autowired
    public DataController(TeamRepository teamRepository, GameweekRepository gameweekRepository, PlayerRepository playerRepository,
                          FixtureRepository fixtureRepository, PlayerGameweekRepository playerGameweekRepository,
                          FantasyTeamService fantasyTeamService, PlayerService playerService) {
        this.teamRepository = teamRepository;
        this.gameweekRepository = gameweekRepository;
        this.playerRepository = playerRepository;
        this.fixtureRepository = fixtureRepository;
        this.playerGameweekRepository = playerGameweekRepository;
        this.fantasyTeamService = fantasyTeamService;
        this.playerService = playerService;
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

            String s = callURL("https://soccer.sportmonks.com/api/v2.0/teams/season/17141?include=squad," +
                    "squad.player,squad.player.position&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J" );
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
            String s = callURL("https://soccer.sportmonks.com/api/v2.0/seasons/17141?include=rounds.fixtures&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J" );
            JsonNode node = mapper.readTree(s);

            List<Gameweek> gameweeks = Arrays.asList(mapper.readValue(node.get("data").get("rounds").get("data").traverse(), Gameweek[].class));
            gameweekRepository.saveAll(gameweeks);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadLive() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = callURL("https://soccer.sportmonks.com/api/v2.0/rounds/" + "194993" +
                    "?include=fixtures.events,fixtures.lineup,fixtures.bench&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J");
            JsonNode node = mapper.readTree(s);
            List<Fixture> fixtures = Arrays.asList(mapper.readValue(node.get("data")
                    .findValue("fixtures").get("data").traverse(), Fixture[].class));

            for (Fixture fixture : fixtures) {
                fixture.getPlayerGameweeks().sort((o1, o2) -> Double.compare(o2.getRating(), o1.getRating()));
                for (PlayerGameweek pg : fixture.getPlayerGameweeks()) {
                    long playerId = pg.getPlayer().getId();
                    if (!playerRepository.existsById(playerId)) {
                        playerService.getPlayerandSave(playerId);
                    }

                  playerGameweekRepository.save(pg);
                }
                fixtureRepository.saveAll(fixtures);
            }
        } catch (IOException e) {
            System.out.println(e);
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
