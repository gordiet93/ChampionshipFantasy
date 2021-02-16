package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
}
