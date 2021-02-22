package com.example.ChampionshipFantasy.deserializer;

import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.service.RepositoryService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PlayerDeserializer extends JsonDeserializer<Player> {

    private RepositoryService repositoryService;

    PlayerDeserializer() {
        this.repositoryService = RepositoryService.instance;
    }

    @Override
    public Player deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);

        return repositoryService.getPlayerReference(node.asLong());
    }
}
