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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@RestController
@Validated
@RequestMapping("/fantasyteams")
public class FantasyTeamController {

    private FantasyTeamService fantasyTeamService;

    @Autowired
    public FantasyTeamController(FantasyTeamService fantasyTeamService) {
        this.fantasyTeamService = fantasyTeamService;
    }

    @GetMapping
    public List<FantasyTeam> findAll() {
        return fantasyTeamService.findAll();
    }

    @GetMapping("/{id}")
    public FantasyTeam findOne(@PathVariable @Min(1) Long id) {
        return fantasyTeamService.findOne(id);
    }

    @GetMapping("/{id}/selections")
    public List<Selection> getSelections(@PathVariable("id") Long id) {
        return fantasyTeamService.getSelections(id);
    }

    @PutMapping("/{id}/selections")
    public void updateSelections(@PathVariable("id") Long id, @RequestBody List<SelectionDto> selectionDtos) {
        fantasyTeamService.updateSelections(id, selectionDtos);
    }
}
