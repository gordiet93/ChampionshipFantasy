package com.example.ChampionshipFantasy.model;


import com.example.ChampionshipFantasy.model.enums.EventType;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Event extends AuditModel {

    @Id
    private Long id;

    @JsonProperty(value = "player_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    @JsonProperty(value = "related_player_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    private Player relatedPlayer;

    @JsonProperty(value = "fixture_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Fixture fixture;

    @JsonProperty(value = "team_id")
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Team team;

    private Integer minute;

    @Enumerated(EnumType.STRING)
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
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
