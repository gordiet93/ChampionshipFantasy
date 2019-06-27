package com.example.ChampionshipFantasy.deserializer;

import com.example.ChampionshipFantasy.model.player.Player;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PlayerListDeserializer extends JsonDeserializer<List<Player>> {

    @Override
    public List<Player> deserialize(JsonParser jsonParser,
                                    DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);

        return Arrays.asList(mapper.readValue(node.get("data").traverse(), Player[].class));
    }
}
