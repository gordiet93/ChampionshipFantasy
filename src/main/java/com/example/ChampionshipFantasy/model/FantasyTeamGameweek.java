package com.example.ChampionshipFantasy.model;

import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class FantasyTeamGameweek {

    @Id
    private Long id;

    @Formula("select SUM(*) ")
    private Integer points;

    @ManyToOne
    private FantasyTeam fantasyTeam;

    @ManyToOne
    private Gameweek gameweek;

    @OneToMany(mappedBy = "fantasyTeamGameweek")
    private List<Selection> selections;
}
