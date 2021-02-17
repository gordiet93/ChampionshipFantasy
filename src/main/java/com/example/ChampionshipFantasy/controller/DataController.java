package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.*;
import com.example.ChampionshipFantasy.model.player.Player;
import com.example.ChampionshipFantasy.repository.*;
import com.example.ChampionshipFantasy.service.FantasyTeamService;
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
import java.sql.SQLOutput;
import java.util.*;

@RestController
@RequestMapping("/data")
public class DataController {

    private TeamRepository teamRepository;
    private GameweekRepository gameweekRepository;
    private PlayerRepository playerRepository;
    private FixtureRepository fixtureRepository;
    private PlayerGameweekRepository playerGameweekRepository;
    private FantasyTeamService fantasyTeamService;

    @Autowired
    public DataController(TeamRepository teamRepository, GameweekRepository gameweekRepository, PlayerRepository playerRepository,
                          FixtureRepository fixtureRepository, PlayerGameweekRepository playerGameweekRepository,
                          FantasyTeamService fantasyTeamService) {
        this.teamRepository = teamRepository;
        this.gameweekRepository = gameweekRepository;
        this.playerRepository = playerRepository;
        this.fixtureRepository = fixtureRepository;
        this.playerGameweekRepository = playerGameweekRepository;
        this.fantasyTeamService = fantasyTeamService;
    }

    @PostMapping("/loaddata")
    public void load() {
        loadTeams();
        loadPlayers();
        loadGameweeks();
        loadFixtures();
    }

//    @PostMapping("/loadlive")
//    public void live() {
//        loadlive();
//    }

    //should be done automatcially, not activited from rest
    //Look into creating one directional relationship between fntteamgmw and selections
    //create a getgameweek method to get the current gameweek, instead of using fantasyteam or selection objects
    @PostMapping("/startGameweek")
    public void startGamweek() {
        fantasyTeamService.createNextGameweekForEachTeam();
    }

    @PostMapping("/endGameweek")
    public void endGameweekAndPrepareNext() {
        fantasyTeamService.updateTotalScores();
    }

    private void loadTeams() {
        ObjectMapper mapper = new ObjectMapper();
        try {

            String s = callURL("https://soccer.sportmonks.com/api/v2.0/teams/season/17141?api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J" );
            JsonNode node = mapper.readTree(s);
            List<Team> teams = Arrays.asList(mapper.readValue(node.get("data").traverse(), Team[].class));
            teamRepository.saveAll(teams);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadPlayers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Team team : teamRepository.findAll()) {
                String s = callURL("https://soccer.sportmonks.com/api/v2.0/squad/season/17141/team/" + team.getId() +
                        "?include=player&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J" );
                JsonNode node = mapper.readTree(s);
                List<Player> players = Arrays.asList(mapper.readValue(node.get("data").traverse(), Player[].class));
                for (Player player : players) {
                    player.setTeam(team);
                }
                playerRepository.saveAll(players);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }



    private void loadGameweeks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = callURL("https://soccer.sportmonks.com/api/v2.0/seasons/17141?include=rounds&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J" );
            JsonNode node = mapper.readTree(s);

            List<Gameweek> gameweeks = Arrays.asList(mapper.readValue(node.get("data").get("rounds").get("data").traverse(), Gameweek[].class));
            gameweekRepository.saveAll(gameweeks);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadFixtures() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Gameweek gameweek : gameweekRepository.findAll()) {
                String s = callURL("https://soccer.sportmonks.com/api/v2.0/rounds/" + gameweek.getId() +
                        "?include=fixtures.events,fixtures.lineup&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J");
                JsonNode node = mapper.readTree(s);
                List<Fixture> fixtures = Arrays.asList(mapper.readValue(node.get("data")
                        .findValue("fixtures").get("data").traverse(), Fixture[].class));
                fixtureRepository.saveAll(fixtures);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadPlayerDetails() {
        for (Player player : playerRepository.findAll()) {

        }
    }

    //make urls constants
    //should run every minute when a game is on to update the player gameweeks
//    private void loadlive() {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//
//            for (Fixture fixture : fixtures) {
//                fixtureRepository.save(fixture);
//                Gameweek gameweek = fixture.getGameweek();
//
//                if (fixture.getLineUps() != null) {
//                    for (LineUp lineup : fixture.getLineUps()) {
//                        Player player = playerRepository.findById(lineup.getPlayerId()).orElse(null);
//
//                        if (player != null) {
//                            player.setName(lineup.getPlayerName());
//                            playerRepository.save(player);
//
//                            PlayerGameweek playerGameweek = playerGameweekRepository.findByPlayerIdAndGameweekId(lineup.getPlayerId(),
//                                    fixture.getGameweek().getId());
//                            //should never need this if statement
//                            if (playerGameweek == null) playerGameweek = new PlayerGameweek(player, gameweek);
//
//                            playerGameweek.setMinutesPlayed(fixture.getMintuesPlayed());
//                            playerGameweekRepository.save(playerGameweek);
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }



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
