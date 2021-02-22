package com.example.ChampionshipFantasy.deserializer;

import com.example.ChampionshipFantasy.model.Event;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventListDe extends StdDeserializer<List<Event>> {

        public EventListDe() {
            this(null);
        }

        public EventListDe(Class<?> vc) {
            super(vc);
        }

        @Override
        public List<Event> deserialize(
                JsonParser jsonparser,
                DeserializationContext context)
                throws IOException, JsonProcessingException {

            return new ArrayList<>();
        }
}

