package com.example.ChampionshipFantasy.service;

import com.example.ChampionshipFantasy.model.Event;
import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.model.PlayerGameweek;
import com.example.ChampionshipFantasy.model.enums.LineUpType;
import com.example.ChampionshipFantasy.model.enums.Position;
import com.example.ChampionshipFantasy.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerGameweekService {

    private PlayerRepository playerRepository;

    @Autowired
    PlayerGameweekService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public int calculateTotalPoints(PlayerGameweek pG, List<Event> goalsConcededByTeam) {
        int total = 0;

        //have to retreive player record DB to obtain its position, work out null warning
        Player player = playerRepository.findById(pG.getPlayer().getId()).orElse(null);
        Position position = player.getPosition();

        pG.setGoalsConceded(calculateGoalsConceded(pG.getLineUpType(), goalsConcededByTeam,
                pG.getSubbedOffEventId(), pG.getSubbedOnEventId()));

        pG.setCleanSheet(checkCleanSheet(pG.getGoalsConceded(), pG.getMinutesPlayed()));

        total += calculateMinutesPlayedPoints(pG.getMinutesPlayed());
        total += pG.getGoalsScored() * position.getGoalPoints();
        total += pG.getPensScored() * position.getGoalPoints();
        total += calculateGoalsConcededPoints(pG.getGoalsConceded(), pG.getCleanSheet(), position);
        total += pG.getAssists() * Player.ASSIST_POINTS;
        total += pG.getOwnGoals() * Player.OWN_GOAL_POINTS;
        total += pG.getMissedPens() * Player.MISSED_PEN_POINTS;
        total += pG.getYellowCards() * Player.YELLOW_CARD_POINTS;
        total += pG.getRedCards() * Player.RED_CARD_POINTS;
        total += pG.getYellowRedCards() * Player.YELLOW_RED_CARD_POINTS;
        total += pG.getBonus();

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

    private int calculateGoalsConceded(LineUpType lineUpType, List<Event> goalsConcededByTeam,
                                       Long subbedOffEventId, Long subbedOnEventId) {
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
