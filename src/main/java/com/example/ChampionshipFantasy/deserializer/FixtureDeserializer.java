package com.example.ChampionshipFantasy.deserializer;

import com.example.ChampionshipFantasy.model.Fixture;
import com.example.ChampionshipFantasy.model.FixtureStatus;
import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.model.Team;
import com.example.ChampionshipFantasy.service.RepositoryService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

        long id = node.get("id").asLong();
        Gameweek gameweek = repositoryService.getGameweekReference(node.get("round_id").asLong());
        Team homeTeam = repositoryService.getTeamReference(node.get("localteam_id").asLong());
        Team awayTeam = repositoryService.getTeamReference(node.get("visitorteam_id").asLong());
        FixtureStatus fixtureStatus = FixtureStatus.valueOf(node.get("time").get("status").textValue());

        return new Fixture(id, gameweek, homeTeam, awayTeam, fixtureStatus);
    }
}
