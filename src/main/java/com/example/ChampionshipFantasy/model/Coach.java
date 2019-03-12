package com.example.ChampionshipFantasy.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Coach")
public class Coach extends Player {
    private static final int GOAL_POINTS = 0;
    private static final int CLEAN_SHEET_POINTS = 0;

    @Override
    public int getGoalPoints() {
        return GOAL_POINTS;
    }

    @Override
    public int getCleanSheetPoints() {
        return CLEAN_SHEET_POINTS;
    }
}
