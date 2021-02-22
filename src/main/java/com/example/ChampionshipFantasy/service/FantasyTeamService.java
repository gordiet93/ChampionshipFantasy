package com.example.ChampionshipFantasy.service;

import com.example.ChampionshipFantasy.dto.SelectionDto;
import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.model.Selection;

import java.util.List;

public interface FantasyTeamService {
    List<FantasyTeam> findAll();
    FantasyTeam findOne(Long id);
    void save(FantasyTeam fantasyTeam);
    void addSelections(Long id, List<SelectionDto> selectionDtos);
    List<Selection> getSelections(Long id);
    void createNextGameweekForEachTeam();
    //void updateTotalScores();
    void updateSelections(Long id, List<SelectionDto> selectionDtos);
}
