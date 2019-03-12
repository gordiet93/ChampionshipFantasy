package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

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

    @Id
    private Long id;
    private String name;
    private String nationality;

    @JsonProperty(value = "teamId")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private Team team;

    @JsonIgnore
    public abstract int getGoalPoints();

    @JsonIgnore
    public abstract int getCleanSheetPoints();

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
