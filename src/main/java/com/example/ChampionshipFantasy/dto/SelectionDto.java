package com.example.ChampionshipFantasy.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SelectionDto {

    @Min(1)
    private Long playerId;

    @NotNull
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
}
