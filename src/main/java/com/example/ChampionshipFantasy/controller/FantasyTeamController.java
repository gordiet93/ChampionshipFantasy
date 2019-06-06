package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.repository.FantasyTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fantasyteam")
public class FantasyTeamController {

    private FantasyTeamRepository fantasyTeamRepository;

    @Autowired
    public FantasyTeamController(FantasyTeamRepository fantasyTeamRepository) {
        this.fantasyTeamRepository = fantasyTeamRepository;
    }

    @GetMapping
    public List<FantasyTeam> findAll() {
        return fantasyTeamRepository.findAll();
    }

    @GetMapping("/{id")
    public FantasyTeam FindOne(@PathVariable("id") Long id) {
        return fantasyTeamRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void save(@RequestBody FantasyTeam fantasyTeam) {
        fantasyTeamRepository.save(fantasyTeam);
    }
}
