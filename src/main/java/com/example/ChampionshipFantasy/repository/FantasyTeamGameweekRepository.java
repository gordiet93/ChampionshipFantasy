package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.FantasyTeamGameweek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FantasyTeamGameweekRepository extends JpaRepository<FantasyTeamGameweek, Long> {

    @Query(
            value = "SELECT * FROM fantasy_team_gameweek f WHERE f.status = Live",
            nativeQuery = true)
    List<FantasyTeamGameweekLive> findAllWithLiveStatus();
}
