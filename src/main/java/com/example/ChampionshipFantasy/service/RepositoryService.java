package com.example.ChampionshipFantasy.service;

import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.model.Team;
import com.example.ChampionshipFantasy.model.player.Player;
import com.example.ChampionshipFantasy.repository.GameweekRepository;
import com.example.ChampionshipFantasy.repository.PlayerRepository;
import com.example.ChampionshipFantasy.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

    public static RepositoryService instance;

    private GameweekRepository gameweekRepository;
    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;

    @Autowired
    public RepositoryService(GameweekRepository gameweekRepository, PlayerRepository playerRepository,
                             TeamRepository teamRepository) {
        this.gameweekRepository = gameweekRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        RepositoryService.instance = this;
    }

    public Team getTeamReference(Long id) {
        return teamRepository.getOne(id);
    }

    public Gameweek getGameweekReference(Long id) {
        return gameweekRepository.getOne(id);
    }

    public Player getPlayerReference(Long id ) { return playerRepository.getOne(id); }
}
