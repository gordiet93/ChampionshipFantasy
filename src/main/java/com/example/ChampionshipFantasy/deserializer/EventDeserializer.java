//package com.example.ChampionshipFantasy.deserializer;
//
//import com.example.ChampionshipFantasy.model.*;
//import com.example.ChampionshipFantasy.model.Event;
//import com.example.ChampionshipFantasy.model.enums.EventType;
//import com.example.ChampionshipFantasy.model.Player;
//import com.example.ChampionshipFantasy.service.RepositoryService;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//
//public class EventDeserializer extends JsonDeserializer<Event> {
//
//    private RepositoryService repositoryService;
//
//    EventDeserializer() { this.repositoryService = RepositoryService.instance; }
//
//    @Override
//    public Event deserialize(JsonParser jsonParser,
//                             DeserializationContext deserializationContext) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode node = mapper.readTree(jsonParser);
//
//        long id = node.get("id").asLong();
//        Team team = new Team(node.get("team_id").asLong());
//
////        Player player = new Player(node.get("player_id").asLong());
////        Player relatedPlayer = new Player(node.get("related_player_id").asLong());
//        int minute = node.get("minute").asInt();
//
//        String eventString = node.get("type").textValue();
//        EventType eventType = EventType.valueOf(eventString);
//
//        return new Event(id, player, relatedPlayer, team, minute, eventType);
//    }
//}
