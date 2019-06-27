package com.example.ChampionshipFantasy.dto;

public class FantasyTeamDto {

    private String name;
    private Long userId;

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
}
