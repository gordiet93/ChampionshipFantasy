package com.example.ChampionshipFantasy.deserializer;

import com.example.ChampionshipFantasy.model.*;
import com.example.ChampionshipFantasy.service.RepositoryService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class FixtureDeserializer extends JsonDeserializer<Fixture> {

    private RepositoryService repositoryService;

    FixtureDeserializer() {
        this.repositoryService = RepositoryService.instance;
    }

    @Override
    public Fixture deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);

        return repositoryService.getFixtureReference((node.asLong()));
    }
}
