package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.player.Player;

import javax.persistence.PrePersist;

public class PlayerGameweekListener {

    @PrePersist
    public void playerGameweekPrePersist(PlayerGameweek pG) {
        pG.setPoints(calculatePoints(pG));
    }

    private int calculatePoints(PlayerGameweek pG) {
        int total = 0;
        Player player = pG.getPlayer();

        total += (pG.getMinutesPlayed() < Player.getMinsThreshold())
                ? Player.getBelowMinsThresholdPoints() : Player.getAboveMinsThresholdPoints();

//        total += pG.getGoalsScored() * player.getGoalPoints();

        return total;
    }
}
