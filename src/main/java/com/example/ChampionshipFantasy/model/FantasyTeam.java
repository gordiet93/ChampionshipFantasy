package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class FantasyTeam extends AuditModel {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Integer totalPoints;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "fantasyTeams",
            targetEntity = League.class
    )
    private List<League> leagues;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne
    private User user;

//    @OneToMany(mappedBy = "fantasyTeam")
//    private List<Pick> picks;

    @OneToMany(mappedBy = "fantasyTeam")
    private List<FantasyTeamGameweek> fantasyTeamGameweeks;

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

//    public List<Pick> getPicks() {
//        return picks;
//    }
//
//    public void setPicks(List<Pick> picks) {
//        this.picks = picks;
//    }
}
