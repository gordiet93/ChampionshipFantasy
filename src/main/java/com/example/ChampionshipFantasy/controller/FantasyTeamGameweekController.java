package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.FantasyTeamGameweek;
import com.example.ChampionshipFantasy.repository.FantasyTeamGameweekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fantasyteamgameweeks")
public class FantasyTeamGameweekController {

    private FantasyTeamGameweekRepository fantasyTeamGameweekRepository;

    @Autowired
    public FantasyTeamGameweekController (FantasyTeamGameweekRepository fantasyTeamGameweekRepository) {
        this.fantasyTeamGameweekRepository = fantasyTeamGameweekRepository;
    }

    @GetMapping("/{id}")
    public FantasyTeamGameweek FindOne(@PathVariable("id") Long id) {
        return fantasyTeamGameweekRepository.findById(id).orElse(null);
    }
}
