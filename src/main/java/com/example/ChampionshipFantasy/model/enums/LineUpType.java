package com.example.ChampionshipFantasy.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LineUpType {
    @JsonProperty("lineup")
    STARTER,
    @JsonProperty("bench")
    BENCHED
}
