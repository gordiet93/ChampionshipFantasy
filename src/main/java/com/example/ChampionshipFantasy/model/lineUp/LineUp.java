package com.example.ChampionshipFantasy.model.lineUp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineUp {

    private long playerId;
    private long teamId;
    private int minutesPlayed;
    private int goalsConceded;
    private double rating;
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

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    @JsonProperty("team_id")
    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @JsonProperty("player_name")
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @JsonProperty("stats")
    private void setDetails(JsonNode stats) {
        minutesPlayed = stats.get("other").get("minutes_played").asInt();
        goalsConceded = stats.get("goals").get("conceded").asInt();
        rating = stats.get("rating").asDouble();
    }

}
