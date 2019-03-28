package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.Competition;
import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.model.Team;
import com.example.ChampionshipFantasy.repository.PlayerRepository;
import com.example.ChampionshipFantasy.repository.TeamRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @PostMapping("/load")
    public void load() {
        loadData();
    }

    public void loadData() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Competition competition = new Competition();
        try {
            competition = mapper.readValue(callURL("http://api.football-data.org/v2/competitions/ELC/teams"), Competition.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        int count = 0;

        if (competition !=null ) {
            for (Team team : competition.getTeams()) {
                count++;

                if (count % 5 == 0) {
                    try {
                        TimeUnit.SECONDS.sleep(30);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }

                Team team1 = new Team();
                try {
                    team1 = mapper.readValue(callURL("http://api.football-data.org/v2/teams/" + team.getId()), Team.class);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                for (Player player : team1.getPlayers()) {
                    player.setTeam(team1);
                }

                teamRepository.save(team1);
            }
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
