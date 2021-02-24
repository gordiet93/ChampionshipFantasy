package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.enums.LineUpType;
import com.example.ChampionshipFantasy.model.enums.Position;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.xml.crypto.dsig.keyinfo.PGPData;
import java.util.ArrayList;
import java.util.List;

public class PlayerGameweekListener {

    public void preUpdate(PlayerGameweek pG) {
        pG.setPoints(calculateStatsAndPoints(pG));
    }

    private int calculateStatsAndPoints(PlayerGameweek pG) {
        calculatePlayerStats(pG);
        return calculateTotalPoints(pG);
    }

    private void calculatePlayerStats(PlayerGameweek pG) {
        Player player = pG.getPlayer();
        long playerId = player.getId();

        //have to check if null as team may not be in database when loading events in the past
        //as the player may have transferred to a different team not in the spl and therefore the team isnt in the DB
        //todo find a clean solution for potential null team
        long playersTeamId = 0;
        if (player.getTeam() != null) playersTeamId = player.getTeam().getId();

        List<Event> events = pG.getFixture().getEvents();
        List<Event> goalsConcededByTeam = new ArrayList<>();

        int goalsScored = 0;
        int assists = 0;
        int pensScored = 0;
        int ownGoals = 0;
        int missedPens = 0;
        int yellowCards = 0;
        int redCards = 0;
        int yellowRedCards = 0;

        for (Event event : events) {
            long eventPlayerId = event.getPlayer().getId();
            long eventTeamId = event.getTeam().getId();
            long eventRelatedPlayerId = 0;
            //todo find clean soltuion for not null
            if (event.getRelatedPlayer() != null) eventRelatedPlayerId = event.getRelatedPlayer().getId();


            if (eventTeamId == playersTeamId) {
                if (eventPlayerId == playerId) {
                    switch (event.getType()) {
                        case GOAL:
                            goalsScored++;
                            break;
                        case PENALTY:
                            pensScored++;
                            break;
                        case YELLOWCARD:
                            yellowCards++;
                            break;
                        case REDCARD:
                            redCards++;
                            break;
                        case YELLOWRED:
                            yellowRedCards++;
                            break;
                        case MISSED_PENALTY:
                            missedPens++;
                            break;
                        case SUBSTITUTION:
                            pG.setSubbedOnEventId(event.getId());
                            break;
                        default:
                    }
                } else if (eventRelatedPlayerId == playerId) {
                    switch (event.getType()) {
                        case GOAL:
                            assists++;
                            break;
                        case SUBSTITUTION:
                            pG.setSubbedOffEventId(event.getId());
                            break;
                        default:
                    }
                }
            } else {
                switch (event.getType()) {
                    case GOAL:
                    case PENALTY:
                        goalsConcededByTeam.add(event);
                        break;
                    case OWNGOAL:
                        if (eventPlayerId == playerId) ownGoals++;
                        goalsConcededByTeam.add(event);
                        break;
                    default:
                }
            }

            //goals conceded not working correctly, says 4 in db when it should be 5
            int goalsConceded = calculateGoalsConcededByPlayer(pG.getLineUpType(), goalsConcededByTeam,
                    pG.getSubbedOffEventId(), pG.getSubbedOnEventId());

            boolean cleanSheet = checkCleanSheet(goalsConceded, pG.getMinutesPlayed());

            //@preupdate is being called the number of times the size of events. need to check why and fix
            System.out.println("in with player id = " + playerId + "event size: " + events.size());

            pG.setCleanSheet(cleanSheet);
            pG.setGoalsScored(goalsScored);
            pG.setAssists(assists);
            pG.setOwnGoals(ownGoals);
            pG.setMissedPens(missedPens);
            pG.setYellowRedCards(yellowRedCards);
            pG.setYellowCards(yellowCards);
            pG.setRedCards(redCards);
            pG.setGoalsConceded(goalsConceded);
            pG.setPensScored(pensScored);
        }
    }

    private int calculateTotalPoints(PlayerGameweek pG) {
        int total = 0;
        Position position = pG.getPlayer().getPosition();

        total += calculateMinutesPlayedPoints(pG.getMinutesPlayed());
        total += pG.getGoalsScored() * position.getGoalPoints();
        total += calculateGoalsConcededPoints(pG.getGoalsConceded(), pG.getCleanSheet(), position);
        total += pG.getAssists() * Player.ASSIST_POINTS;
        total += pG.getOwnGoals() * Player.OWN_GOAL_POINTS;
        total += pG.getMissedPens() * Player.MISSED_PEN_POINTS;
        total += pG.getYellowCards() * Player.YELLOW_CARD_POINTS;
        total += pG.getRedCards() * Player.RED_CARD_POINTS;
        total += pG.getYellowRedCards() * Player.YELLOW_RED_CARD_POINTS;

        return total;
    }

    private int calculateMinutesPlayedPoints(int minsPlayed) {
        return (minsPlayed == 0 ? Player.ZERO_MINS_POINTS : minsPlayed < Player.MINS_THRESHOLD
                ? Player.BELOW_MINS_THRESHOLD_POINTS : Player.ABOVE_MINS_THRESHOLD_POINTS);
    }

    private boolean checkCleanSheet(int goalsConceded, int minsPlayed) {
        return (goalsConceded == 0 && minsPlayed >= Player.MINS_THRESHOLD);
    }

    private int calculateGoalsConcededPoints(int goalsConceded, boolean cleanSheet, Position position) {
        if (cleanSheet) {
            return position.getCleanSheetPoints();
        } else if (position == Position.GOALKEEPER || position == Position.DEFENDER) {
            return -(goalsConceded / 2);
        }
        return 0;
    }

    private int calculateGoalsConcededByPlayer(LineUpType lineUpType, List<Event> goalsConcededByTeam, Long subbedOffEventId, Long subbedOnEventId) {
        int goalsConceded = 0;
        if (goalsConcededByTeam.isEmpty()) {
            return goalsConceded;
        }

        switch (lineUpType) {
            case STARTER:
                if (subbedOffEventId == null) goalsConceded = goalsConcededByTeam.size();
                else for (Event event : goalsConcededByTeam) {
                    if (event.getId() < subbedOffEventId) goalsConceded++;
                }
                break;
            case BENCHED:
                if (subbedOnEventId != null && subbedOffEventId != null) {
                    for (Event event : goalsConcededByTeam) {
                        if (event.getId() > subbedOnEventId && event.getId() < subbedOffEventId)
                            goalsConceded++;
                    }
                } else if (subbedOnEventId != null) {
                    for (Event event : goalsConcededByTeam) {
                        if (event.getId() > subbedOnEventId) goalsConceded++;
                    }
                }
                break;
        }
        return goalsConceded;
    }
}
