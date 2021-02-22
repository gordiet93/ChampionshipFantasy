package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.PlayerGameweek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
@Service
public interface PlayerGameweekRepository extends JpaRepository<PlayerGameweek, Long> {

    PlayerGameweek findByPlayerIdAndFixtureId(Long playerId, Long fixtureId);


}
