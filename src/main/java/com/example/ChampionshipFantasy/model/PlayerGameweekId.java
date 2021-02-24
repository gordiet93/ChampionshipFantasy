package com.example.ChampionshipFantasy.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

//todo implement an override equals and hashcode methods
@Embeddable
public class PlayerGameweekId implements Serializable {

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "fixture_id")
    private Long fixtureId;

    public PlayerGameweekId() {
    }

    public PlayerGameweekId(Long playerId, Long fixtureId) {
        this.playerId = playerId;
        this.fixtureId = fixtureId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(Long fixtureId) {
        this.fixtureId = fixtureId;
    }
}