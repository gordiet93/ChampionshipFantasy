package com.example.ChampionshipFantasy.model.player;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Goalkeeper")
public class GoalKeeper extends Player {
    private static final int GOAL_POINTS = 7;
    private static final int CLEAN_SHEET_POINTS = 4;

    @Override
    public int getGoalPoints() {
        return GOAL_POINTS;
    }

    @Override
    public int getCleanSheetPoints() {
        return CLEAN_SHEET_POINTS;
    }
}
