package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.Fixture;
import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.model.PlayerGameweek;
import com.example.ChampionshipFantasy.model.player.Player;
import com.example.ChampionshipFantasy.model.Team;
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

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameweekRepository gameweekRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private FixtureRepository fixtureRepository;

    @Autowired
    private PlayerGameweekRepository playerGameweekRepository;

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
            JsonNode node = mapper.readTree(new File("src\\main\\resources\\TeamAndPlayer.json"));
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
            JsonNode node = mapper.readTree(new File("src\\main\\resources\\gameweeks.json"));
            List<Gameweek> teamList = Arrays.asList(mapper.readValue(node.get("data").traverse(), Gameweek[].class));

            for (Gameweek gameweek : teamList) {
                gameweekRepository.save(gameweek);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadlive() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(new File("src\\main\\resources\\HeartsHibsLive.json"));
            JsonNode nodeaye = node.get("data").findValue("lineup").findValue("data");

            Gameweek gameweek = gameweekRepository.getOne(node.get("data").findValue("round_id").asLong());

            for (JsonNode aye : nodeaye) {
                Player player = playerRepository.findById(aye.findValue("player_id").asLong()).orElse(null);

                if (player != null) {
                    player.setName(aye.findValue("player_name").toString());
                    playerRepository.save(player);
                }

                playerGameweekRepository.save(new PlayerGameweek(player, gameweek, 0, 0, 0));
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadFixtures() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(new File("src\\main\\resources\\RangersUpcomingfixture.json"));
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
