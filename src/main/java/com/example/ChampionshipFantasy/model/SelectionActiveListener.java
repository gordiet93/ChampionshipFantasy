package com.example.ChampionshipFantasy.model;

import org.springframework.stereotype.Service;

import javax.persistence.PostLoad;

@Service
public class SelectionActiveListener {

    private static final int CAPTAIN_MULTI = 2;

    @PostLoad
    public void postLoad(SelectionActive selectionActive) {
        selectionActive.setPoints(calculatePoints(selectionActive));
    }

    private int calculatePoints(SelectionActive selectionActive) {
        PlayerGameweek playerGameweek = selectionActive.getPlayer().getPlayerGameweekMap().get(
                selectionActive.getGameweek());

        int total = playerGameweek.getPoints();
        if (selectionActive.isCaptained()) {
            total = total * CAPTAIN_MULTI;
        }
        return total;
    }
 }
