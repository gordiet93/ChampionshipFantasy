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

    public static final int ZERO_MINS_POINTS = 0;
    public static final int ABOVE_MINS_THRESHOLD_POINTS = 2;
    public static final int BELOW_MINS_THRESHOLD_POINTS = 1;
    public static final int MINS_THRESHOLD = 60;
    public static final int ASSIST_POINTS = 3;
    public static final int YELLOW_CARD_POINTS = -1;
    public static final int RED_CARD_POINTS = -3;
    public static final int YELLOW_RED_CARD_POINTS = -2;
    public static final int OWN_GOAL_POINTS = -2;
    public static final int MISSED_PEN_POINTS = -3;

    @Id
    @JsonProperty("player_id")
    private Long id;

    @JsonProperty("display_name")
    private String name;

    @JsonProperty("nationality")
    private String nationality;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="player")
    private List<PlayerGameweek> playerGameweeks;

    //@JsonIdentityReference(alwaysAsId = true)
    //@JsonBackReference
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JsonProperty(value = "team_id")
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

    @JsonProperty("position")
    private void setDetails(JsonNode node) {
        position = Position.valueOf(node.get("data").get("name").asText().toUpperCase());
    }

    //todo implement overridden equals method, then replace player.getid = pg.getplayer.getid etc
}
