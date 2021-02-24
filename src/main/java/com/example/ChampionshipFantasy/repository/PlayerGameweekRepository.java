package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.Fixture;
import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.model.PlayerGameweek;
import com.example.ChampionshipFantasy.model.PlayerGameweekId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
@Service
public interface PlayerGameweekRepository extends JpaRepository<PlayerGameweek, Long> {

    PlayerGameweek findByPlayerAndFixture(Player player, Fixture fixture);
    PlayerGameweek findByPlayerIdAndFixtureId(Long playerId, Long fixtureId);
    PlayerGameweek findByPlayerGameweekId(PlayerGameweekId playerGameweekId);


}
