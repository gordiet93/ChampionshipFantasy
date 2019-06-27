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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private TeamRepository teamRepository;
    private GameweekRepository gameweekRepository;
    private PlayerRepository playerRepository;
    private FixtureRepository fixtureRepository;
    private PlayerGameweekRepository playerGameweekRepository;

    @Autowired
    public DataController(TeamRepository teamRepository, GameweekRepository gameweekRepository, PlayerRepository playerRepository,
                          FixtureRepository fixtureRepository, PlayerGameweekRepository playerGameweekRepository) {
        this.teamRepository = teamRepository;
        this.gameweekRepository = gameweekRepository;
        this.playerRepository = playerRepository;
        this.fixtureRepository = fixtureRepository;
        this.playerGameweekRepository = playerGameweekRepository;
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
            List<Gameweek> gameweekList = Arrays.asList(mapper.readValue(node.get("data").traverse(), Gameweek[].class));

            for (Gameweek gameweek : gameweekList) {
                gameweekRepository.save(gameweek);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadlive() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode base = mapper.readTree(new File("src/main/resources/HeartsHibsLive.json"));
            JsonNode lineup = base.get("data").findValue("lineup").findValue("data");

            Fixture fixture = mapper.readValue(base.get("data").traverse(), Fixture.class);
            fixtureRepository.save(fixture);

            Gameweek gameweek = fixture.getGameweek();

            for (JsonNode jsonNode : lineup) {
                Player player = playerRepository.findById(jsonNode.findValue("player_id").asLong()).orElse(null);

                if (player != null) {
                    player.setName(jsonNode.findValue("player_name").textValue());
                    playerRepository.save(player);

                    PlayerGameweek playerGameweek = player.getPlayerGameweekMap().get(gameweek);
                    if (playerGameweek == null) playerGameweek = new PlayerGameweek(player, gameweek);

                    playerGameweek.setMinutesPlayed(jsonNode.get("stats").get("other").findValue("minutes_played").asInt());
                    playerGameweekRepository.save(playerGameweek);

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
        con.setRequestProperty("X-Auth-Token", "bc2a33c0a22244ce83c272a2e9562655");
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
