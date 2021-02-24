package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.deserializer.PlayerListDeserializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.javafx.binding.SelectBinding;

import javax.persistence.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Entity
// not used atm, needed for
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id",
//        scope = Team.class)
public class Team extends AuditModel {

    @Id
    private Long id;
    private String name;

    @JsonProperty("squad")
    @JsonDeserialize(using = PlayerListDeserializer.class)
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Player> players;

    public Team() {}

    public Team(Long id) {
        this.id = id;
    }

    //need this constructor as sportmonks api json is sometimes read as a string for team_id
    public Team(String id) {
        this.id = Long.valueOf(id);
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
