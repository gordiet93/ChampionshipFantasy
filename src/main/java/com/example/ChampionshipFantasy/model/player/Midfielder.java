package com.example.ChampionshipFantasy.model.player;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Midfielder")
public class Midfielder extends Player {
    private static final int GOAL_POINTS = 5;
    private static final int CLEAN_SHEET_POINTS = 1;

    @Override
    public int getGoalPoints() {
        return GOAL_POINTS;
    }

    @Override
    public int getCleanSheetPoints() {
        return CLEAN_SHEET_POINTS;
    }
}
