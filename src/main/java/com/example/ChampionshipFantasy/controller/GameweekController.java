package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.repository.GameweekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gameweeks")
public class GameweekController {

    @Autowired
    private GameweekRepository gameweekRepository;

    @GetMapping("/{id}")
    public Gameweek findOne(@PathVariable("id") Long id) {
        return gameweekRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void save(@RequestBody Gameweek gameweek) {
        gameweekRepository.save(gameweek);
    }
}
