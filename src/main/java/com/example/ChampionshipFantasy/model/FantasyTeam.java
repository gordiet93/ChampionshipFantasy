package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class FantasyTeam extends AuditModel {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Integer totalPoints;

    @JsonProperty(value = "fantasyPlayerId")
    @JsonIdentityReference(alwaysAsId = true)
//    @JoinColumn(name = "fantasy_player_id", insertable = false, updatable = false)
    @OneToOne(targetEntity = FantasyPlayer.class, fetch = FetchType.LAZY)
    private FantasyPlayer fantasyPlayer;

//    @Column(name = "fantasy_player_id")
//    private Long fantasyPlayerId;

    @OneToMany
    private List<Player> players;

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

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

//    public Long getFantasyPlayerId() {
//        return fantasyPlayerId;
//    }
//
//    public void setFantasyPlayerId(Long fantasyPlayerId) {
//        this.fantasyPlayerId = fantasyPlayerId;
//    }

    public FantasyPlayer getFantasyPlayer() {
        return fantasyPlayer;
    }

    public void setFantasyPlayer(FantasyPlayer fantasyPlayer) {
        this.fantasyPlayer = fantasyPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
