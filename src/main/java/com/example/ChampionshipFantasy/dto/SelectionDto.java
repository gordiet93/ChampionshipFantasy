package com.example.ChampionshipFantasy.dto;

public class SelectionDto {

    private Long playerId;
    private Long gameweekId;
    private boolean captained;

    public SelectionDto() {
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public boolean isCaptained() {
        return captained;
    }

    public void setCaptained(boolean captained) {
        this.captained = captained;
    }

    public Long getGameweekId() {
        return gameweekId;
    }

    public void setGameweekId(Long gameweekId) {
        this.gameweekId = gameweekId;
    }
}
