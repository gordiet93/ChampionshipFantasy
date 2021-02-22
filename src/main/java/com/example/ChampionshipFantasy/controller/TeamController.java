package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.model.Team;
import com.example.ChampionshipFantasy.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private TeamRepository teamRepository;

    @Autowired
    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @GetMapping("/{id}")
    public Team findOne(@PathVariable("id") Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/players")
    public List<Player> findOnePlayers(@PathVariable("id") Long id) {
        return teamRepository.findById(id).orElse(null).getPlayers();
    }
}
