package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.model.League;
import com.example.ChampionshipFantasy.repository.LeagueRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("leagues")
public class LeagueContoller {

    private LeagueRepository leagueRepository;

    public LeagueContoller(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @GetMapping("/{id}")
    public League findOne(Long id) {
        return leagueRepository.getOne(id);
    }

    @GetMapping("/{id}/fantasyteams")
    public List<FantasyTeam> getFantasyTeams(Long id) {
        return leagueRepository.getOne(id).getFantasyTeams();
    }
}
