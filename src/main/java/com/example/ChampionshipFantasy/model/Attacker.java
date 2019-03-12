package com.example.ChampionshipFantasy.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Attacker")
public class Attacker extends Player {
    private static final int GOAL_POINTS = 4;
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
