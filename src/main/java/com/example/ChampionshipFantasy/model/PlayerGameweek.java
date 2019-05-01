package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.player.Player;

import javax.persistence.*;

@Entity
@EntityListeners(PlayerGameweekListener.class)
public class PlayerGameweek extends AuditModel {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Gameweek gameweek;

    private Integer points;
    private Integer goalsScored;
    private Integer assists;
    private Integer minutesPlayed;

    public PlayerGameweek(Player player, Gameweek gameweek, Integer goalsScored, Integer assists, Integer minutesPlayed) {
        this.player = player;
        this.gameweek = gameweek;
        this.goalsScored = goalsScored;
        this.assists = assists;
        this.minutesPlayed = minutesPlayed;
    }

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
