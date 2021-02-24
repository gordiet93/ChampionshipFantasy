package com.example.ChampionshipFantasy.deserializer;

import com.example.ChampionshipFantasy.model.Player;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerListDeserializer extends JsonDeserializer<List<Player>> {

    @Override
    public List<Player> deserialize(JsonParser jsonParser,
                                    DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);
        JsonNode jsonNode = node.get("data");

        List<Player> players = new ArrayList<>();

        for (JsonNode playerNode : jsonNode) {
            Player player = mapper.readValue(playerNode.get("player").get("data").traverse(), Player.class);
            players.add(player);
        }

        return players;
    }
}
