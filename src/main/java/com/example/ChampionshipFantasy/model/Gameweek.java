package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gameweek implements Serializable {

    @Id
    private Long id;

    @JsonProperty(value = "start")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date startDate;

    @JsonProperty(value = "end")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date endDate;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "gameweek")
    private List<Fixture> fixtures;

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

    public List<Fixture> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
    }

    @JsonProperty("fixtures")
    private void setFixtures(JsonNode data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        fixtures = Arrays.asList(mapper.readValue(data.get("data").traverse(), Fixture[].class));
    }

}
