package com.example.ChampionshipFantasy.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Gameweek implements Serializable {

    @Id
    private Long id;

    public Gameweek() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
