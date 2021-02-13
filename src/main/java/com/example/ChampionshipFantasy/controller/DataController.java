package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.*;
import com.example.ChampionshipFantasy.model.player.Player;
import com.example.ChampionshipFantasy.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.PostLoad;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private TeamRepository teamRepository;
    private GameweekRepository gameweekRepository;
    private PlayerRepository playerRepository;
    private FixtureRepository fixtureRepository;
    private PlayerGameweekRepository playerGameweekRepository;
    private FantasyTeamRepository fantasyTeamRepository;
    private FantasyTeamGameweekRepository fantasyTeamGameweekRepository;

    @Autowired
    public DataController(TeamRepository teamRepository, GameweekRepository gameweekRepository, PlayerRepository playerRepository,
                          FixtureRepository fixtureRepository, PlayerGameweekRepository playerGameweekRepository,
                          FantasyTeamRepository fantasyTeamRepository, FantasyTeamGameweekRepository fantasyTeamGameweekRepository) {
        this.teamRepository = teamRepository;
        this.gameweekRepository = gameweekRepository;
        this.playerRepository = playerRepository;
        this.fixtureRepository = fixtureRepository;
        this.playerGameweekRepository = playerGameweekRepository;
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.fantasyTeamGameweekRepository = fantasyTeamGameweekRepository;
    }

    @PostMapping("/loadteamsandplayers")
    public void load() {
        loadData();
    }

    @PostMapping("/loadgameweeks")
    public void gameweeks() {
        loadGameweeks();
    }

    @PostMapping("/loadlive")
    public void live() {
        loadlive();
    }

    @PostMapping("/loadfixtures")
    public void fix() {
        loadFixtures();
    }

    //should be done automatcially, not activited from rest
    //Look into creating one directional relationship between fntteamgmw and selections
    //create a getgameweek method to get the current gameweek, instead of using fantasyteam or selection objects
    @PostMapping("/startGameweek")
    public void startGamweek() {
        List<FantasyTeam> fantasyTeams = fantasyTeamRepository.findAll();
        //Gameweek nextGameweek = gameweekRepository.findFirstByStartDateAfter(new Date(System.currentTimeMillis()));
        Long gameweekNum = 173644L;
        Gameweek gameweek = gameweekRepository.getOne(gameweekNum);

        for (FantasyTeam fantasyTeam : fantasyTeams) {
            FantasyTeamGameweekLive fantasyTeamGameweekLive = new FantasyTeamGameweekLive(fantasyTeam, gameweek,
                    false, fantasyTeam.getSelections());
            fantasyTeamGameweekRepository.save(fantasyTeamGameweekLive);
        }
    }

    @PostMapping("/endGameweek")
    public void endGameweekAndPrepareNext() {
        List<FantasyTeamGameweekLive> fantasyTeamGameweekLives = fantasyTeamGameweekRepository.findAllWithLiveStatus();
        List<Player> players = playerRepository.findAll();
        Gameweek nextGamweek = gameweekRepository.findFirstByStartDateAfter(new Date(System.currentTimeMillis()));

        for (Player player : players) {
            playerGameweekRepository.save(new PlayerGameweek(player, nextGamweek));
        }

        for (FantasyTeamGameweekLive fantasyTeamGameweekLive : fantasyTeamGameweekLives) {
            FantasyTeamGameweek fantasyTeamGameweek = fantasyTeamGameweekLive;

        }
    }

    //refactor
    private void loadData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(new File("src/main/resources/TeamAndPlayer.json"));
            List<Team> teamList = Arrays.asList(mapper.readValue(node.get("data").traverse(), Team[].class));

            for (Team team : teamList) {
                for (Player player : team.getPlayers()) {
                    player.setTeam(team);
                }
                teamRepository.save(team);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadGameweeks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(new File("src/main/resources/gameweeks.json"));
            List<Gameweek> gameweekList = Arrays.asList(mapper.readValue(node.get("data").get("rounds").get("data").traverse(), Gameweek[].class));

            for (Gameweek gameweek : gameweekList) {
                gameweekRepository.save(gameweek);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //make urls constants
    private void loadlive() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //String s = callURL("https://soccer.sportmonks.com/api/v2.0/livescores?leagues=501&include=lineup&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J");
            JsonNode base = mapper.readTree(new File("src/main/resources/HeartsHibsLive.json"));
            List<Fixture> fixtures = Arrays.asList(mapper.readValue(base.get("data").traverse(), Fixture[].class));

            for (Fixture fixture : fixtures) {
                fixtureRepository.save(fixture);
                Gameweek gameweek = fixture.getGameweek();

                if (fixture.getLineUps() != null) {
                    for (LineUp lineup : fixture.getLineUps()) {
                        Player player = playerRepository.findById(lineup.getPlayerId()).orElse(null);

                        if (player != null) {
                            player.setName(lineup.getPlayerName());
                            playerRepository.save(player);

                            PlayerGameweek playerGameweek = playerGameweekRepository.findByPlayerIdAndGameweekId(lineup.getPlayerId(),
                                    fixture.getGameweek().getId());
                            //should never need this if statement
                            if (playerGameweek == null) playerGameweek = new PlayerGameweek(player, gameweek);

                            playerGameweek.setMinutesPlayed(fixture.getMintuesPlayed());
                            playerGameweekRepository.save(playerGameweek);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadFixtures() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(new File("src/main/resources/RangersUpcomingfixture.json"));
            JsonNode nodeaye = node.get("data").findValue("upcoming").findValue("data");

            List<Fixture> fixtures = Arrays.asList(mapper.readValue(nodeaye.traverse(), Fixture[].class));

            fixtureRepository.saveAll(fixtures);
        } catch (IOException e) {
            System.out.println(e);
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
