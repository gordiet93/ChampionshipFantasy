package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@EntityListeners(SelectionActiveListener.class)
@PrimaryKeyJoinColumn(name = "id")
public class SelectionActive extends Selection {

    @ManyToOne
    @JsonIgnore
    private FantasyTeam fantasyTeam;

    public SelectionActive() {}

    public SelectionActive(Player player, Boolean captained) {
        this.setPlayer(player);
        this.setCaptained(captained);
    }

    public FantasyTeam getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(FantasyTeam fantasyTeam) {
        this.fantasyTeam = fantasyTeam;
    }
}
