package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.PlayerGameweek;
import com.example.ChampionshipFantasy.repository.PlayerGameweekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playergameweeks")
public class PlayerGameweekController {

    @Autowired
    private PlayerGameweekRepository playerGameweekRepository;

    @GetMapping("/{id}")
    public PlayerGameweek findOne(@PathVariable("id") Long id) {
        return playerGameweekRepository.findById(id).orElse(null);
    }

}
