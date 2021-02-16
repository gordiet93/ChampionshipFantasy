package com.example.ChampionshipFantasy.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class FantasyTeamDto {

    @NotEmpty
    private String name;

    @Valid
    private List<SelectionDto> selections;

    public FantasyTeamDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectionDto> getSelections() {
        return selections;
    }

    public void setSelections(List<SelectionDto> selections) {
        this.selections = selections;
    }
}
