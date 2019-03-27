package com.example.ChampionshipFantasy.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class FantasyTeamGameweek {

    @Id
    private Long id;

    private FantasyTeam fantasyTeam;

    private List<Pick> picks;
}
