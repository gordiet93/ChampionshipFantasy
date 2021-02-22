package com.example.ChampionshipFantasy.model.enums;

public enum Position {
    GOALKEEPER (7, 4),
    DEFENDER (6, 4),
    MIDFIELDER (5, 1),
    ATTACKER (4, 0);

    private final int goalPoints;
    private final int cleanSheetPoints;

    Position(int goalPoints, int cleanSheetPoints) {
        this.goalPoints = goalPoints;
        this.cleanSheetPoints = cleanSheetPoints;
    }

    public int getGoalPoints() {
        return goalPoints;
    }

    public int getCleanSheetPoints() {
        return cleanSheetPoints;
    }

}
