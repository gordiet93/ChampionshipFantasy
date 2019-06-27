package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(scope = Gameweek.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Gameweek implements Serializable {

    @Id
    private Long id;

    public Gameweek() {}

    public Gameweek(Long id) {
        this.id = id;
    }

    public void setId(Long id)  {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
