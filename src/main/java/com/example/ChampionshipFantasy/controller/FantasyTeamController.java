package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.repository.FantasyTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fantasyteam")
public class FantasyTeamController {

    @Autowired
    private FantasyTeamRepository fantasyTeamRepository;

    @GetMapping
    public List<FantasyTeam> findAll() {
        return fantasyTeamRepository.findAll();
    }

    @GetMapping("/{id")
    public FantasyTeam FindOne(@PathVariable("id") Long id) {
        return fantasyTeamRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void saveFantasyTeam(FantasyTeam fantasyTeam) {
        fantasyTeamRepository.save(fantasyTeam);
    }
}
