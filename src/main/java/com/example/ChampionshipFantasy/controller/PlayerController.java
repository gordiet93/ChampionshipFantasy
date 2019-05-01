package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.player.Player;
import com.example.ChampionshipFantasy.model.PlayerSummary;
import com.example.ChampionshipFantasy.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
