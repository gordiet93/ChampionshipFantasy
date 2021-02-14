package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

@Entity
public class FantasyTeamGameweek {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private Integer points;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "fantasyTeam_id")
    private FantasyTeam fantasyTeam;

    @ManyToOne
    @JsonProperty(value = "gameweek_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Gameweek gameweek;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Selection> selections;

    private Boolean tripleCaptain;

    public FantasyTeamGameweek() {}

    public FantasyTeamGameweek(FantasyTeam fantasyTeam, Gameweek gameweek, Boolean tripleCaptain,
                               List<Selection> selections) {
        this.fantasyTeam = fantasyTeam;
        this.gameweek = gameweek;
        this.tripleCaptain = tripleCaptain;
        this.selections = selections;
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

    public FantasyTeam getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(FantasyTeam fantasyTeam) {
        this.fantasyTeam = fantasyTeam;
    }

    public Gameweek getGameweek() {
        return gameweek;
    }

    public void setGameweek(Gameweek gameweek) {
        this.gameweek = gameweek;
    }

    public List<Selection> getSelections() {
        return selections;
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    public Boolean getTripleCaptain() {
        return tripleCaptain;
    }

    public void setTripleCaptain(Boolean tripleCaptain) {
        this.tripleCaptain = tripleCaptain;
    }
}
