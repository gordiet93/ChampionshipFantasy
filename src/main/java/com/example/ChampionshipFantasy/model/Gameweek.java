package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.*;

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

    @JsonProperty(value = "start")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date startDate;

    @JsonProperty(value = "end")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date endDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
