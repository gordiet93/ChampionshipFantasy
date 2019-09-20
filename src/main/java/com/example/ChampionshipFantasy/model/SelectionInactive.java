package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.player.Player;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class SelectionInactive extends Selection {

    @ManyToOne
    @JsonIgnore
    private FantasyTeamGameweek fantasyTeamGameweek;

    public SelectionInactive() {}

    public SelectionInactive(Integer points, FantasyTeamGameweek fantasyTeamGameweek, Gameweek gameweek, Player player,
                             Boolean captained) {
        this.setPoints(points);
        this.fantasyTeamGameweek = fantasyTeamGameweek;
        this.setGameweek(gameweek);
        this.setPlayer(player);
        this.setCaptained(captained);
    }

    public FantasyTeamGameweek getFantasyTeamGameweek() {
        return fantasyTeamGameweek;
    }

    public void setFantasyTeamGameweek(FantasyTeamGameweek fantasyTeamGameweek) {
        this.fantasyTeamGameweek = fantasyTeamGameweek;
    }
}
