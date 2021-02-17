package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.player.Player;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team extends AuditModel {

    @Id
    private Long id;
    private String name;

    @JsonProperty(value = "squad", access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Player> players;

    public Team() {
    }

    public Team(Long id) {
        this.id = id;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
