package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="\"User\"")
public class User extends AuditModel {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String email;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    @PrimaryKeyJoinColumn
    private FantasyTeam fantasyTeam;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FantasyTeam getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(FantasyTeam fantasyTeam) {
    }
}
