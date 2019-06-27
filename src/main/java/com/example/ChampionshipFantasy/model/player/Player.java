package com.example.ChampionshipFantasy.model.player;

import com.example.ChampionshipFantasy.model.*;
import com.example.ChampionshipFantasy.model.Event;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "player_id")
@DiscriminatorColumn(name = "position", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "position_id")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Attacker.class, name = "4"),
        @JsonSubTypes.Type(value = Midfielder.class, name = "3"),
        @JsonSubTypes.Type(value = Defender.class, name = "2"),
        @JsonSubTypes.Type(value = GoalKeeper.class, name = "1")
})
public abstract class Player extends AuditModel {

    private static final int ABOVE_MINS_THRESHOLD_POINTS = 2;
    private static final int BELOW_MINS_THRESHOLD_POINTS = 1;
    private static final int MINS_THRESHOLD = 60;
    private static final int ASSIST_POINTS = 4;

    @Id
    @JsonProperty("player_id")
    private Long id;
    private String name;
    private String nationality;
    private Integer Number;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="player")
    @MapKey(name = "gameweek")
    private Map<Long, PlayerGameweek> playerGameweekMap;

    @JsonProperty(value = "team_Id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private Team team;

    @JsonIgnore
    public abstract int getGoalPoints();

    @JsonIgnore
    public abstract int getCleanSheetPoints();

    public static int getAboveMinsThresholdPoints() {
        return ABOVE_MINS_THRESHOLD_POINTS;
    }

    public static int getBelowMinsThresholdPoints() {
        return BELOW_MINS_THRESHOLD_POINTS;
    }

    public static int getMinsThreshold() {
        return MINS_THRESHOLD;
    }

    public static int getAssistPoints() {
        return ASSIST_POINTS;
    }

    public Map<Long, PlayerGameweek> getPlayerGameweekMap() {
        return playerGameweekMap;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }
}
