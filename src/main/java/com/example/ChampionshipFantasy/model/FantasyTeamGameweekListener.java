package com.example.ChampionshipFantasy.model;

import javax.persistence.PrePersist;

public class FantasyTeamGameweekListener {

    @PrePersist
    public void prePersist(FantasyTeamGameweek fantasyTeamGameweek) {
        fantasyTeamGameweek.setPoints(calculatePoints(fantasyTeamGameweek));
    }

    private int calculatePoints(FantasyTeamGameweek fantasyTeamGameweek) {
        int total = 0;

        for (SelectionInactive selection : fantasyTeamGameweek.getSelections()) {
            total += selection.getPoints();
        }

        return total;
    }
}
