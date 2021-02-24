package com.example.ChampionshipFantasy.service;

import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.repository.PlayerRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    public Player getPlayerandSave(long playerId) {
        Player player = new Player();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = callURL("https://soccer.sportmonks.com/api/v2.0/players/" + playerId +
                    "?include=position&api_token=udPYhTkSHKOk36Oy4Dz1NrZZ6aICE0ffzdtk8lsLkcLUR6DPcfE68beqyQ4J" );
            JsonNode node = mapper.readTree(s);
            player = mapper.readValue(node.get("data").traverse(), Player.class);

            playerRepository.save(player);
        } catch (IOException e) {
            System.out.println(e);
        }
        return player;
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


