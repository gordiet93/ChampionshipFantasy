package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.enums.Position;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class Player extends AuditModel {

    static final int ZERO_MINS_POINTS = 0;
    static final int ABOVE_MINS_THRESHOLD_POINTS = 2;
    static final int BELOW_MINS_THRESHOLD_POINTS = 1;
    static final int MINS_THRESHOLD = 60;
    static final int ASSIST_POINTS = 3;
    static final int YELLOW_CARD_POINTS = -1;
    static final int RED_CARD_POINTS = -3;
    static final int YELLOW_RED_CARD_POINTS = -2;
    static final int OWN_GOAL_POINTS = -2;
    static final int MISSED_PEN_POINTS = -3;

    @Id
    @JsonProperty("player_id")
    private Long id;
    private String name;
    private String nationality;
    private Integer number;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="player")
    private List<PlayerGameweek> playerGameweeks;

    //@JsonIdentityReference(alwaysAsId = true)
    //@JsonBackReference
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private Team team;

    @Enumerated(EnumType.STRING)
    private Position position;

    public Player() {}

    public Player(Long id) {
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<PlayerGameweek> getPlayerGameweeks() {
        return playerGameweeks;
    }

    public void setPlayerGameweeks(List<PlayerGameweek> playerGameweeks) {
        this.playerGameweeks = playerGameweeks;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @JsonProperty("player")
    private void setDetails(JsonNode node) {
        name = node.get("data").get("display_name").asText();
        nationality = node.get("data").get("nationality").asText();
        position = Position.valueOf(node.get("data").get("position").get("data").get("name").asText().toUpperCase());
        this.team = new Team(node.get("data").get("team_id").longValue());
    }
}
