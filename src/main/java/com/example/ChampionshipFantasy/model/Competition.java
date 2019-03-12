package com.example.ChampionshipFantasy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Competition {

    @JsonProperty("competition")
    private CompetitionInfo competitionInfo;

    @JsonProperty("teams")
    private List<Team> teams;

    public CompetitionInfo getCompetitionInfo() {
        return competitionInfo;
    }

    public void setCompetitionInfo(CompetitionInfo competitionInfo) {
        this.competitionInfo = competitionInfo;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
