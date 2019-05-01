package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.PlayerGameweek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerGameweekRepository extends JpaRepository<PlayerGameweek, Long> {
}
