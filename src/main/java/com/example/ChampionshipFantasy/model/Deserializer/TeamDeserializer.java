package com.example.ChampionshipFantasy.model.Deserializer;

import com.example.ChampionshipFantasy.model.Team;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class TeamDeserializer extends StdDeserializer<Team> {

    public TeamDeserializer() {
        this(null);
    }

    public TeamDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Team deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode located = node.path("squad");
        Team team = new Team();
        team.setId((node.get("id")).asLong());
        team.setName(node.get("name").asText());
        team.setVenue(node.get("venue").asText());


        return null;
    }
}
