package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(FantasyTeamGameweekListener.class)
@Entity
public class FantasyTeamGameweek {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private Integer points;

    @ManyToOne
    private FantasyTeam fantasyTeam;

    @ManyToOne
    private Gameweek gameweek;

    @OneToMany(mappedBy = "fantasyTeamGameweek", cascade = CascadeType.ALL)
    private List<SelectionInactive> selections;

    private Boolean tripleCaptain;

    public FantasyTeamGameweek() {}

    public FantasyTeamGameweek(Integer points, FantasyTeam fantasyTeam, Gameweek gameweek,
                               List<SelectionInactive> selections, Boolean tripleCaptain) {
        this.points = points;
        this.fantasyTeam = fantasyTeam;
        this.gameweek = gameweek;
        this.selections = selections;
        this.tripleCaptain = tripleCaptain;
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

    public List<SelectionInactive> getSelections() {
        return selections;
    }

    public void setSelections(List<SelectionInactive> selections) {
        this.selections = selections;
    }
}
