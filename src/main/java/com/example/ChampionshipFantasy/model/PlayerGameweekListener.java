package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.player.Defender;
import com.example.ChampionshipFantasy.model.player.GoalKeeper;
import com.example.ChampionshipFantasy.model.player.Player;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

public class PlayerGameweekListener {

    @PrePersist
    public void prePersist(PlayerGameweek pG) {
        pG.setPoints(calculatePoints(pG));
    }

    @PostLoad
    public void postLoad(PlayerGameweek pG) {
        pG.setPoints(calculatePoints(pG));
    }

    private int calculatePoints(PlayerGameweek pG) {
        int total = 0;
        Player player = pG.getPlayer();

        int minsPlayed = pG.getMinutesPlayed();
        total += (minsPlayed == 0 ? Player.getZeroMinsPoints() : minsPlayed < Player.getMinsThreshold()
                ? Player.getBelowMinsThresholdPoints() : Player.getAboveMinsThresholdPoints());

        int goalsConceded = pG.getGoalsConceded();
        if (goalsConceded == 0 && pG.getMinutesPlayed() >= Player.getMinsThreshold()) {
            total += player.getCleanSheetPoints();
        } else if (player instanceof Defender || player instanceof GoalKeeper) {
            total -= goalsConceded / 2;
        }

        if (pG.getRedCards() == 1) {
            total += Player.getRedCardPoints();
        } else {
            total += pG.getYellowCards() * Player.getYellowCardPoints();
        }

        total += pG.getOwnGoals() * Player.getOwnGoalPoints();

        total += pG.getGoalsScored() * player.getGoalPoints();

        total += pG.getAssists() * Player.getAssistPoints();

        return total;
    }
}
