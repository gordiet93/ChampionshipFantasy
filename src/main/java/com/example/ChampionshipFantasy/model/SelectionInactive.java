package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class SelectionInactive extends Selection {

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JsonProperty(value = "fantasyTeamGameweek_Id")
    private FantasyTeamGameweek fantasyTeamGameweek;

    public FantasyTeamGameweek getFantasyTeamGameweek() {
        return fantasyTeamGameweek;
    }

    public void setFantasyTeamGameweek(FantasyTeamGameweek fantasyTeamGameweek) {
        this.fantasyTeamGameweek = fantasyTeamGameweek;
    }
}
