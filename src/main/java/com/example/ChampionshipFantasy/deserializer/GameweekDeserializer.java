package com.example.ChampionshipFantasy.deserializer;

import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.service.RepositoryService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GameweekDeserializer extends JsonDeserializer<Gameweek> {

    private RepositoryService repositoryService;

    GameweekDeserializer() {
        this.repositoryService = RepositoryService.instance;
    }

    @Override
    public Gameweek deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);

        return repositoryService.getGameweekReference(node.asLong());
    }
}
