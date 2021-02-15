package com.example.ChampionshipFantasy.service;

import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.repository.GameweekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GameweekServiceImpl implements GameweekService {

    private GameweekRepository gameweekRepository;

    @Autowired
    public GameweekServiceImpl(GameweekRepository gameweekRepository) {
        this.gameweekRepository = gameweekRepository;
    }

    public Gameweek getCurrentGameweek() {
        return gameweekRepository.findByStartDateEquals(new Date(System.currentTimeMillis()));
    }
}
