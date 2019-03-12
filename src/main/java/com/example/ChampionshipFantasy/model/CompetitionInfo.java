package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompetitionInfo {

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;
}
