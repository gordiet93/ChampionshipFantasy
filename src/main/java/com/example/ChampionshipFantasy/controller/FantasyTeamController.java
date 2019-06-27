package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.dto.FantasyTeamDto;
import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.model.SelectionActive;
import com.example.ChampionshipFantasy.repository.FantasyTeamRepository;
import com.example.ChampionshipFantasy.repository.SelectionRepository;
import com.example.ChampionshipFantasy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fantasyteams")
public class FantasyTeamController {

    private FantasyTeamRepository fantasyTeamRepository;
    private UserRepository userRepository;
    private FantasyTeamRepository fantasyTeamRepository1;

    @Autowired
    public FantasyTeamController(FantasyTeamRepository fantasyTeamRepository, UserRepository userRepository) {
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<FantasyTeam> findAll() {
        return fantasyTeamRepository.findAll();
    }

    @GetMapping("/{id}")
    public FantasyTeam FindOne(@PathVariable("id") Long id) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.findById(id).orElse(null);
        fantasyTeamRepository.save(fantasyTeam);
        return fantasyTeam;
    }

    @GetMapping("/{id}/selections")
    public List<SelectionActive> GetSelections(@PathVariable("id") Long id) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.findById(id).orElse(null);
        return fantasyTeam.getSelections();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void save(@RequestBody FantasyTeamDto fantasyTeamDto) {
        fantasyTeamRepository.save(dtoToModel(fantasyTeamDto));
    }

    private FantasyTeam dtoToModel(FantasyTeamDto fantasyTeamDto) {
        return new FantasyTeam(fantasyTeamDto.getName(), userRepository.getOne(fantasyTeamDto.getUserId()));
    }
}
