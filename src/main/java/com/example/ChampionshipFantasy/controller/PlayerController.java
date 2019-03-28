package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.Player;
import com.example.ChampionshipFantasy.model.PlayerGameweek;
import com.example.ChampionshipFantasy.model.PlayerSummary;
import com.example.ChampionshipFantasy.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @GetMapping("/summary")
    public List<PlayerSummary> summary() {
        return playerRepository.findSummary();
    }

    @GetMapping("/{id}")
    public Player findOne(@PathVariable("id") Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/gameweeks")
    public Map<Long, PlayerGameweek> findPlayerGameweeks(@PathVariable("id") Long id) {
        return playerRepository.findById(id).orElse(null).getPlayerGameweekMap();
    }

    @PostMapping("/gameweeks")
    public void addPlayerGameweek(@RequestBody PlayerGameweek playerGameweek) {
        playerRepository.findById(3838L).orElse(null).getPlayerGameweekMap().put(playerGameweek.getGameweek().getId(), playerGameweek);
    }
}
