package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.enums.LineUpType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import javax.persistence.*;
import java.io.Serializable;

@EntityListeners(PlayerGameweekListener.class)
@Entity
public class PlayerGameweek extends AuditModel implements Serializable {

    @EmbeddedId
    private PlayerGameweekId playerGameweekId;

    @ManyToOne
    @JoinColumn(name = "player_id", insertable = false, updatable = false)
    private Player player;

    @JsonProperty("team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "fixture_id", insertable = false, updatable = false)
    private Fixture fixture;

    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private LineUpType lineUpType;

    private Integer goalsScored;
    private Integer assists;
    private Integer ownGoals;
    private Integer yellowCards;
    private Integer redCards;
    private Integer yellowRedCards;
    private Integer missedPens;
    private Integer pensScored;
    private Long subbedOnEventId;
    private Long subbedOffEventId;
    private Boolean cleanSheet;
    private Integer goalsConceded;
    private Double rating;
    private Integer bonus;
    private Integer minutesPlayed;
    private Integer points;

    public PlayerGameweek(PlayerGameweekId playerGameweekId) {
        this.playerGameweekId = playerGameweekId;
    }

    @JsonCreator
    public PlayerGameweek(@JsonProperty("player_id") final Player player,
                          @JsonProperty("fixture_id") final Fixture fixture) {
        this.player = player;
        this.fixture = fixture;
        this.playerGameweekId = new PlayerGameweekId(player.getId(), fixture.getId());
    }

    public PlayerGameweek() {
    }

    public PlayerGameweekId getPlayerGameweekId() {
        return playerGameweekId;
    }

    public void setPlayerGameweekId(PlayerGameweekId playerGameweekId) {
        this.playerGameweekId = playerGameweekId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public Integer getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(Integer goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public Integer getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(Integer minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public Integer getOwnGoals() {
        return ownGoals;
    }

    public void setOwnGoals(Integer ownGoals) {
        this.ownGoals = ownGoals;
    }

    public Integer getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(Integer yellowCards) {
        this.yellowCards = yellowCards;
    }

    public Integer getRedCards() {
        return redCards;
    }

    public void setRedCards(Integer redCards) {
        this.redCards = redCards;
    }

    public Long getSubbedOnEventId() {
        return subbedOnEventId;
    }

    public void setSubbedOnEventId(Long subbedOnEventId) {
        this.subbedOnEventId = subbedOnEventId;
    }

    public Long getSubbedOffEventId() {
        return subbedOffEventId;
    }

    public void setSubbedOffEventId(Long subbedOffEventId) {
        this.subbedOffEventId = subbedOffEventId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public double getRating() {
        return rating;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public LineUpType getLineUpType() {
        return lineUpType;
    }

    public void setLineUpType(LineUpType lineUpType) {
        this.lineUpType = lineUpType;
    }

    public Integer getMissedPens() {
        return missedPens;
    }

    public void setMissedPens(Integer missedPens) {
        this.missedPens = missedPens;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getYellowRedCards() {
        return yellowRedCards;
    }

    public void setYellowRedCards(Integer yellowRedCards) {
        this.yellowRedCards = yellowRedCards;
    }

    public void setCleanSheet(Boolean cleanSheet) {
        this.cleanSheet = cleanSheet;
    }

    public Boolean getCleanSheet() {
        return cleanSheet;
    }

    public Integer getPensScored() {
        return pensScored;
    }

    public void setPensScored(Integer pensScored) {
        this.pensScored = pensScored;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @JsonProperty("stats")
    private void setDetails(JsonNode stats) {
        minutesPlayed = stats.get("other").get("minutes_played").asInt();
        goalsConceded = stats.get("goals").get("conceded").asInt();
        goalsScored = stats.get("goals").get("scored").asInt();
        assists = stats.get("goals").get("assists").asInt();
        ownGoals = stats.get("goals").get("owngoals").asInt();
        yellowCards = stats.get("cards").get("yellowcards").asInt();
        redCards = stats.get("cards").get("redcards").asInt();
        yellowRedCards = stats.get("cards").get("yellowredcards").asInt();
        missedPens = stats.get("other").get("pen_missed").asInt();
        pensScored = stats.get("other").get("pen_scored").asInt();
        rating = stats.get("rating").asDouble();
        rating = stats.get("rating").asDouble();
    }
}
