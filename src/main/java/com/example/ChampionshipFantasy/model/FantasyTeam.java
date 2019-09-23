package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class FantasyTeam extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @Formula("(SELECT SUM(f.points) FROM fantasy_team_gameweek f WHERE f.fantasy_team_id = id)")
    private Integer totalPoints;

    @JsonProperty(value = "user_id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fantasyTeams",
            targetEntity = League.class
    )
    private List<League> leagues;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "fantasyTeam")
    private List<FantasyTeamGameweek> fantasyTeamGameweeks;

    @OneToMany(mappedBy = "fantasyTeam", cascade = CascadeType.ALL)
    private List<SelectionActive> selections;

    public FantasyTeam() {
    }

    public FantasyTeam(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SelectionActive> getSelections() {
        return selections;
    }

    public void setSelections(List<SelectionActive> selections) {
        this.selections = selections;
    }

    public List<FantasyTeamGameweek> getFantasyTeamGameweeks() {
        return fantasyTeamGameweeks;
    }

    public void setFantasyTeamGameweeks(List<FantasyTeamGameweek> fantasyTeamGameweeks) {
        this.fantasyTeamGameweeks = fantasyTeamGameweeks;
    }
}
