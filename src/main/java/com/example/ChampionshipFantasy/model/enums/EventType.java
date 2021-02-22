package com.example.ChampionshipFantasy.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventType {
    SUBSTITUTION,
    YELLOWCARD,
    YELLOWRED,
    REDCARD,
    GOAL,
    PENALTY,
    @JsonProperty("own-goal")
    OWNGOAL,
    MISSED_PENALTY
}
