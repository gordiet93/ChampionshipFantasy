package com.example.ChampionshipFantasy.service;

import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.model.Team;
import com.example.ChampionshipFantasy.repository.GameweekRepository;
import com.example.ChampionshipFantasy.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

    public static RepositoryService instance;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameweekRepository gameweekRepository;

    public RepositoryService() {
        RepositoryService.instance = this;
    }

    public Team getTeamReference(Long id) {
        return teamRepository.getOne(id);
    }

    public Gameweek getGameweekReference(Long id) {
        return gameweekRepository.getOne(id);
    }
}