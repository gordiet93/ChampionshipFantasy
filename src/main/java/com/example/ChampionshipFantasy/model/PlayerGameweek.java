package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.player.Player;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@EntityListeners(PlayerGameweekListener.class)
public class PlayerGameweek extends AuditModel {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("player_id")
    private Player player;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("gameweek_id")
    private Gameweek gameweek;

    @Formula("(SELECT count(*) FROM event e WHERE e.player_id = player_id AND e.gameweek_id = gameweek_id AND e.type = 3)")
    private Integer goalsScored;

    @Formula("(SELECT count(*) FROM event e WHERE e.related_player_id = player_id AND e.gameweek_id = gameweek_id AND e.type = 3)")
    private Integer assists;

    private Integer minutesPlayed;

    private Integer points;

    public PlayerGameweek(Player player, Gameweek gameweek) {
        this.player = player;
        this.gameweek = gameweek;
        this.goalsScored = 0;
        this.assists = 0;
        this.minutesPlayed = 0;
    }

    public PlayerGameweek() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Gameweek getGameweek() {
        return gameweek;
    }

    public void setGameweek(Gameweek gameweek) {
        this.gameweek = gameweek;
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

    public Integer getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(Integer minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }
}
