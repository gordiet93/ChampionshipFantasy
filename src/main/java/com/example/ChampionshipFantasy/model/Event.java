package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.deserializer.EventDeserializer;
import com.example.ChampionshipFantasy.model.*;
import com.example.ChampionshipFantasy.model.player.Player;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@JsonDeserialize(using = EventDeserializer.class)
public class Event extends AuditModel {

    @Id
    private Long id;

    @ManyToOne
    @JsonProperty(value = "player_id")
    private Player player;

    @ManyToOne
    @JsonProperty(value = "related_player_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Player relatedPlayer;

    @ManyToOne
    private Fixture fixture;

    @ManyToOne
    private Gameweek gameweek;

    @ManyToOne
    private Team team;

    private Integer minute;
    private EventType type;

    public Event(Long id, Player player, Player relatedPlayer, Team team, Integer minute, EventType type) {
        this.id = id;
        this.player = player;
        this.relatedPlayer = relatedPlayer;
        this.team = team;
        this.minute = minute;
        this.type = type;
    }

    public Event() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getRelatedPlayer() {
        return relatedPlayer;
    }

    public void setRelatedPlayer(Player relatedPlayer) {
        this.relatedPlayer = relatedPlayer;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public Gameweek getGameweek() {
        return gameweek;
    }

    public void setGameweek(Gameweek gameweek) {
        this.gameweek = gameweek;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
