package com.example.ChampionshipFantasy.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDto {

    @NotEmpty
    private String name;

    @Email
    private String email;

    @Valid
    private FantasyTeamDto fantasyTeam;

    public UserDto() {
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

    public FantasyTeamDto getFantasyTeamDto() {
        return fantasyTeam;
    }

    public void setFantasyTeamDto(FantasyTeamDto fantasyTeamDto) {
        this.fantasyTeam = fantasyTeamDto;
    }
}
