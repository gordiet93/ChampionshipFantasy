package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.dto.FantasyTeamDto;
import com.example.ChampionshipFantasy.dto.SelectionDto;
import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.model.Selection;
import com.example.ChampionshipFantasy.repository.*;
import com.example.ChampionshipFantasy.service.FantasyTeamService;
import com.example.ChampionshipFantasy.service.FantasyTeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/fantasyteams")
public class FantasyTeamController {

    private FantasyTeamService fantasyTeamService;
    private UserRepository userRepository;

    @Autowired
    public FantasyTeamController(UserRepository userRepository,
                                 FantasyTeamService fantasyTeamService) {
        this.fantasyTeamService = fantasyTeamService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<FantasyTeam> findAll() {
        return fantasyTeamService.findAll();
    }

    @GetMapping("/{id}")
    public FantasyTeam FindOne(@PathVariable("id") Long id) {
        return fantasyTeamService.findOne(id);
    }

    //Eventually validate so the size of the selections is 11 to start, then 15.
    @GetMapping("/{id}/selections")
    public List<Selection> GetSelections(@PathVariable("id") Long id) {
        return fantasyTeamService.getSelections(id);
    }

    //Eventually validate so the size of the selections is 11 to start, then 15.
    @PostMapping("/{id}/selections")
    public void addSelections(@PathVariable("id") Long id, @RequestBody List<SelectionDto> selectionDtos) {
        fantasyTeamService.addSelections(id, selectionDtos);
    }

    @PutMapping("/{id}/selections")
    public void updateSelections(@PathVariable("id") Long id, @RequestBody List<SelectionDto> selectionDtos) {
        fantasyTeamService.updateSelections(id, selectionDtos);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void save(@RequestBody FantasyTeamDto fantasyTeamDto) {
        fantasyTeamService.save(new FantasyTeam(fantasyTeamDto.getName(),
                userRepository.getOne(fantasyTeamDto.getUserId())));
    }
}
