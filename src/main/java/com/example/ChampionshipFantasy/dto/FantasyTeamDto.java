package com.example.ChampionshipFantasy.dto;

import com.example.ChampionshipFantasy.model.Selection;

import java.util.List;

public class FantasyTeamDto {

    private String name;
    private Long userId;
    private List<SelectionDto> selections;

    public FantasyTeamDto() {
    }

    public FantasyTeamDto(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userID) {
        this.userId = userID;
    }

    public List<SelectionDto> getSelections() {
        return selections;
    }

    public void setSelections(List<SelectionDto> selections) {
        this.selections = selections;
    }
}
