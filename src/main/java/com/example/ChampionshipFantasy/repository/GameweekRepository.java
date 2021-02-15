package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.Gameweek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GameweekRepository extends JpaRepository<Gameweek, Long> {
    List<Gameweek> findAll();

    Gameweek findByStartDateEquals(@Param("currentDate") Date currentDate);

    Gameweek findFirstByStartDateAfter(@Param("currentDate") Date currentDate);
}
