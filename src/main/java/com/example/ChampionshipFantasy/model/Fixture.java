package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.deserializer.EventListDe;
import com.example.ChampionshipFantasy.model.enums.FixtureStatus;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fixture {

    @Id
    private Long id;

    @JsonProperty(value = "round_id")
    @ManyToOne
    private Gameweek gameweek;

    @JsonProperty(value = "localteam_id")
    @ManyToOne
    private Team homeTeam;

    @JsonProperty(value = "visitorteam_id")
    @ManyToOne
    private Team awayTeam;

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL)
    private List<Event> events;

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL)
    private List<PlayerGameweek> playerGameweeks;

    private FixtureStatus status;

    @Transient
    private Integer mintuesPlayed;

    public Fixture() {}

    public Fixture(Long id) {
        this.id = id;
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<PlayerGameweek> getPlayerGameweeks() {
        return playerGameweeks;
    }

    public void setPlayerGameweeks(List<PlayerGameweek> playerGameweeks) {
        this.playerGameweeks = playerGameweeks;
    }

    public FixtureStatus getStatus() {
        return status;
    }

    public void setStatus(FixtureStatus status) {
        this.status = status;
    }

    public Integer getMintuesPlayed() {
        return mintuesPlayed;
    }

    public void setMintuesPlayed(Integer mintuesPlayed) {
        this.mintuesPlayed = mintuesPlayed;
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

    @JsonProperty("events")
    private void setEvents(JsonNode data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        events = Arrays.asList(mapper.readValue(data.get("data").traverse(), Event[].class));
    }
//
//    @JsonProperty("lineup")
//    @JsonAlias("bench")
//    private void setPlayerGameweeks(JsonNode data) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        playerGameweeks.addAll(Arrays.asList(mapper.readValue(data.get("data").traverse(), PlayerGameweek[].class)));
//    }
}

