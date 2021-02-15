package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.PlayerGameweek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface PlayerGameweekRepository extends JpaRepository<PlayerGameweek, Long> {
    PlayerGameweek findByPlayerIdAndGameweekId(Long playerId, Long gameweekId);

    List<PlayerGameweek> findByGameweekId(Long gameweekId);
}
