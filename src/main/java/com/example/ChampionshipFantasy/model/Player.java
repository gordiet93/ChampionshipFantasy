package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "position", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "position")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Attacker.class, name = "Attacker"),
        @JsonSubTypes.Type(value = Midfielder.class, name = "Midfielder"),
        @JsonSubTypes.Type(value = Defender.class, name = "Defender"),
        @JsonSubTypes.Type(value = GoalKeeper.class, name = "Goalkeeper"),
        @JsonSubTypes.Type(value = Coach.class, name = "null")
})
public abstract class Player extends AuditModel {

    private static final int ABOVE_MINS_THRESHOLD_POINTS = 2;
    private static final int BELOW_MINS_THRESHOLD_POINTS = 1;
    private static final int MINS_THRESHOLD = 60;

    @Id
    private Long id;
    private String name;
    private String nationality;

//    @OneToMany(mappedBy = "player")
//    private List<PlayerGameweek> playerGameweeks;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="player")
    @MapKey(name = "gameweek")
    private Map<Long, PlayerGameweek> playerGameweekMap;

    @JsonProperty(value = "teamId")
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
}
