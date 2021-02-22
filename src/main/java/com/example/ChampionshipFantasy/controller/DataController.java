package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.*;
import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.model.enums.LineUpType;
import com.example.ChampionshipFantasy.repository.*;
import com.example.ChampionshipFantasy.service.FantasyTeamService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private void loadTeams() {
        ObjectMapper mapper = new ObjectMapper();
        try {

            String s = callURL("https://soccer.sportmonks.com/api/v2.0/teams/season/17141?include=squad&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J" );
            JsonNode node = mapper.readTree(s);
            List<Team> teams = Arrays.asList(mapper.readValue(node.get("data").traverse(), Team[].class));
            teamRepository.saveAll(teams);
            System.out.println("yes");

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadPlayers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Team team : teamRepository.findAll()) {
                String s = callURL("https://soccer.sportmonks.com/api/v2.0/squad/season/17141/team/" + team.getId() +
                        "?include=player.position&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J" );
                JsonNode node = mapper.readTree(s);
                List<Player> players = Arrays.asList(mapper.readValue(node.get("data").traverse(), Player[].class));
//                for (Player player : players) {
////                    player.setTeam(team);
////                }
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
            //for (Gameweek gameweek : gameweekRepository.findAll()) {
            String s = callURL("https://soccer.sportmonks.com/api/v2.0/rounds/" + "194968" +
                    "?include=fixtures&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J");
            JsonNode node = mapper.readTree(s);
            List<Fixture> fixtures = Arrays.asList(mapper.readValue(node.get("data")
                    .findValue("fixtures").get("data").traverse(), Fixture[].class));
            fixtureRepository.saveAll(fixtures);
            // }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadLive() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = callURL("https://soccer.sportmonks.com/api/v2.0/rounds/" + "194968" +
                    "?include=fixtures.events,fixtures.lineup,fixtures.bench&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J");
            JsonNode node = mapper.readTree(s);
            List<Fixture> fixtures = Arrays.asList(mapper.readValue(node.get("data")
                    .findValue("fixtures").get("data").traverse(), Fixture[].class));
//
//            for (Fixture fixture : fixtures) {
//                fixture.getPlayerGameweeks().sort((o1, o2) -> Double.compare(o2.getRating(), o1.getRating()));
//
//                playerGameweekRepository.saveAll(fixture.getPlayerGameweeks());
//            }
            fixtureRepository.saveAll(fixtures);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


//    make urls constants
//    should run every minute when a game is on to update the player gameweeks
//    private void live() {
//        //should get the current gameweek
//        //load all the fixtures in the current gameweek, call their api and then go through each one updating the playergameweeks
//        //for testing just doing all the gameweeks
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            for (Gameweek gameweek : gameweekRepository.findAll()) {
//                String s = callURL("https://soccer.sportmonks.com/api/v2.0/rounds/" + gameweek.getId() +
//                        "?include=fixtures.events,fixtures.lineup,fixtures.bench&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J");
//                JsonNode node = mapper.readTree(s);
//                List<Fixture> fixtures = Arrays.asList(mapper.readValue(node.get("data")
//                        .findValue("fixtures").get("data").traverse(), Fixture[].class));
//
//                //could maybe call all off the playergameweeks at once and save all together,
//                // so only two DB calls instead of a lot
//               for (Fixture fixture : fixtures) {
//
//
//                   List<Event> goals = new ArrayList<>();
//                   List<Event> subs = new ArrayList<>();
//
////                   switch (fixture.getEvents().)
//
//                   for (LineUp lineUp : fixture.getLineUps()) {
//                       PlayerGameweek playerGameweek = playerGameweekRepository.findByPlayerIdAndFixtureId(lineUp.getPlayerId(),
//                               fixture.getId());
//                       if (playerGameweek != null) {
//
//                           //work out total goals conceded here
//
//                           int goalsConceded = 0;
//
//
//
//
//                           List<Event> goals = new ArrayList<>();
//                           List<Event> subs = new ArrayList<>();
//                           for (Event event : fixture.getEvents()) {
//                               switch (event.getType()) {
//                                   case GOAL:
//                                       if (!event.getTeam().getId().equals(lineUp.getTeamId())) goals.add(event);
//                                       break;
//                                   case SUBSTITUTION:
//
//                                       if (event.getRelatedPlayer().getId().equals(lineUp.getPlayerId())) subs.add(event);
//                                       break;
//                                   default:
//                               }
//
//                           }
//
//                           if (!goals.isEmpty()) {
//                               switch (lineUp.getType()) {
//                                   case STARTER:
//                                       if (subs.isEmpty()) goalsConceded = goals.size();
//                                       else for (Event goal : goals) {
//                                           if (goal.getId() < subs.get(0).getId()) goalsConceded++;
//                                       }
//                                       break;
//                                   case BENCHED:
//
//                               }
//                           }
//
//
//                           if (!goals.isEmpty()) {
//                               if (lineUp instanceof Starter) {
//                                   for (Event goal : goals) {
//                                       if (goal.getMinute() < lineUp.getMinutesPlayed()) {
//                                           goalsConceded++;
//                                       } else if(goal.getMinute() == lineUp.getMinutesPlayed()) {
//                                           for (Event sub : subs) {
//                                               if (goal.getId() < sub.getId()) goalsConceded++;
//                                           }
//                                       }
//                                   }
//                               }
//                           }
//
//                           playerGameweek.setMinutesPlayed(lineUp.getMinutesPlayed());
//                           playerGameweek.setGoalsConceded(goalsConceded);
//                           playerGameweekRepository.save(playerGameweek);
//                       }
//                   }
//               }
//            }
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }

//    private void createAllPlayerGameweeks() {
//        Gameweek gameweek = gameweekRepository.getOne(194968L);
//        for (Fixture fixture : gameweek.getFixtures()) {
//            for (Player player : playerRepository.findAll()) {
//                PlayerGameweek playerGameweek = new PlayerGameweek(player, fixture);
//                playerGameweekRepository.save(playerGameweek);
//            }
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
