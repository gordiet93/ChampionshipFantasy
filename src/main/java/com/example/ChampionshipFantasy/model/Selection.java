package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.player.Player;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Selection extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    private Integer points;

    private Gameweek gameweek;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JsonProperty(value = "player_Id")
    private Player player;
    private boolean captained;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isCaptained() {
        return captained;
    }

    public void setCaptained(boolean captained) {
        this.captained = captained;
    }

    public Gameweek getGameweek() {
        return gameweek;
    }

    public void setGameweek(Gameweek gameweek) {
        this.gameweek = gameweek;
    }
}
