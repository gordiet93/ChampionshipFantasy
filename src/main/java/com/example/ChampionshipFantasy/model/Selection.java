package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Selection extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    private Integer points;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JsonProperty(value = "player_gameweek_id")
    private PlayerGameweek playerGameweek;

    private boolean captained;

    public Selection() {}

    public Selection(PlayerGameweek playerGameweek, boolean captained) {
        this.playerGameweek = playerGameweek;
        this.captained = captained;
    }

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

    public PlayerGameweek getPlayerGameweek() {
        return playerGameweek;
    }

    public void setPlayerGameweek(PlayerGameweek playerGameweek) {
        this.playerGameweek = playerGameweek;
    }

    public boolean isCaptained() {
        return captained;
    }

    public void setCaptained(boolean captained) {
        this.captained = captained;
    }
}
