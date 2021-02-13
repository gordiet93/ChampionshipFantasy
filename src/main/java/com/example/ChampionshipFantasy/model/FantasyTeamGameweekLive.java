package com.example.ChampionshipFantasy.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.List;

@Entity
@EntityListeners(FantasyTeamGameweekLiveListener.class)
@DiscriminatorValue("Live")
public class FantasyTeamGameweekLive extends FantasyTeamGameweek {
    public FantasyTeamGameweekLive(FantasyTeam fantasyTeam, Gameweek gameweek, Boolean tripleCaptain,
                                   List<Selection> selections) {
        setFantasyTeam(fantasyTeam);
        setGameweek(gameweek);
        setTripleCaptain(tripleCaptain);
        setSelections(selections);
    }
}
