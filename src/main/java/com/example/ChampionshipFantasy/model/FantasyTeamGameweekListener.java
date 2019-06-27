package com.example.ChampionshipFantasy.model;

import javax.persistence.PostLoad;

public class FantasyTeamGameweekListener {

    @PostLoad
    public void postLoad(FantasyTeamGameweek fantasyTeamGameweek) {
        fantasyTeamGameweek.setPoints(calculatePoints(fantasyTeamGameweek));
    }

    private int calculatePoints(FantasyTeamGameweek fantasyTeamGameweek) {
        int total = 0;

        for (Selection selection : fantasyTeamGameweek.getSelections()) {
            total += selection.getPoints();
        }

        return total;
    }
}
