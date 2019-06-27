package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.Gameweek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameweekRepository extends JpaRepository<Gameweek, Long> {
    List<Gameweek> findAll();
}
