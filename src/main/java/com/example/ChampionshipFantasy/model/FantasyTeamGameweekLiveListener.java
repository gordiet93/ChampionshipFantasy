package com.example.ChampionshipFantasy.model;

import javax.persistence.PostLoad;

/*public class FantasyTeamGameweekLiveListener {

    private static final int CAPTAIN_MULTI = 2;

    @PostLoad
    public void prePersist(FantasyTeamGameweekLive fantasyTeamGameweekLive) {
        fantasyTeamGameweekLive.setPoints(calculateTotalPoints(fantasyTeamGameweekLive));
    }

    private int calculateTotalPoints(FantasyTeamGameweekLive fantasyTeamGameweekLive) {
        int total = 0;
        for (Selection selection : fantasyTeamGameweekLive.getSelections()) {
            selection.setPoints(calculateSelectionPoints(selection));
            total += selection.getPoints();
        }
        return total;
    }

    private int calculateSelectionPoints(Selection selection) {
        int total = selection.getPlayerGameweek().getPoints();
        if (selection.isCaptained()) {
            total = total * CAPTAIN_MULTI;
        }
        return total;
    }
}*/
