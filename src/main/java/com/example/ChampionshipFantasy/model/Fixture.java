package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.deserializer.FixtureDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = FixtureDeserializer.class)
public class Fixture {

    @Id
    private Long id;

    @ManyToOne
    private Gameweek gameweek;

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team awayTeam;

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL)
    private List<Event> events;

    private FixtureStatus status;

    @Transient
    private List<LineUp> lineUps;

    @Transient
    private Integer mintuesPlayed;

    public Fixture() {}

    public Fixture(Long id, Gameweek gameweek, Team homeTeam, Team awayTeam, FixtureStatus status, int mintuesPlayed) {
        this.id = id;
        this.gameweek = gameweek;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.status = status;
        this.mintuesPlayed = mintuesPlayed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gameweek getGameweek() {
        return gameweek;
    }

    public void setGameweek(Gameweek gameweek) {
        this.gameweek = gameweek;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public FixtureStatus getStatus() {
        return status;
    }

    public void setStatus(FixtureStatus status) {
        this.status = status;
    }
//
//    @JsonProperty("time")
//    private void mapFixtureStatus(Map<String, Object> time) {
//        this.status = FixtureStatus.valueOf(time.get("status").toString());
//    }
//
//    @JsonProperty("round_id")
//    private void setGameweek(Long id) {
//        this.gameweek = new Gameweek(id);
//    }

    public List<LineUp> getLineUps() {
        return lineUps;
    }

    public void setLineUps(List<LineUp> lineUps) {
        this.lineUps = lineUps;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Integer getMintuesPlayed() {
        return mintuesPlayed;
    }

    public void setMintuesPlayed(Integer mintuesPlayed) {
        this.mintuesPlayed = mintuesPlayed;
    }
}
