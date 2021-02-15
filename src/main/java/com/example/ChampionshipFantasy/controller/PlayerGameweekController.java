package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.model.PlayerGameweek;
import com.example.ChampionshipFantasy.repository.PlayerGameweekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playergameweeks")
public class PlayerGameweekController {

    private PlayerGameweekRepository playerGameweekRepository;

    @Autowired
    public PlayerGameweekController(PlayerGameweekRepository playerGameweekRepository) {
        this.playerGameweekRepository = playerGameweekRepository;
    }

    @GetMapping("/{id}")
    public PlayerGameweek findOne(@PathVariable("id") Long id) {
        return playerGameweekRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<PlayerGameweek> findByGameweek(@RequestParam("gameweekid") Long id) {
        return playerGameweekRepository.findByGameweekId(id);
    }
}
