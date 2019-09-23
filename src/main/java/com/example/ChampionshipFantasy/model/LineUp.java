package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineUp {

    private long playerId;
    private int mintuesplayed;
    private String playerName;

    public LineUp() {
    }

    public long getPlayerId() {
        return playerId;
    }

    @JsonProperty("player_id")
    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getMintuesplayed() {
        return mintuesplayed;
    }

    //A bit messy, look to improve later
    @JsonProperty("stats")
    public void setMintuesPlayedFromNestedObject(Map<String, Object> stats) {
        this.mintuesplayed = 90; //move into constructor
       // mintuesplayed += Integer.valueOf(((Map<String, Object>)stats.get("other")).get("minutes_played").toString());
    }

    @JsonProperty("player_name")
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
