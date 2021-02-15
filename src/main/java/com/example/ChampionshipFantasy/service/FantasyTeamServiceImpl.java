package com.example.ChampionshipFantasy.service;

import com.example.ChampionshipFantasy.dto.SelectionDto;
import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.model.FantasyTeamGameweek;
import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.model.Selection;
import com.example.ChampionshipFantasy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FantasyTeamServiceImpl implements FantasyTeamService {

    private FantasyTeamRepository fantasyTeamRepository;
    private FantasyTeamGameweekRepository fantasyTeamGameweekRepository;
    private GameweekRepository gameweekRepository;
    private PlayerGameweekRepository playerGameweekRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public FantasyTeamServiceImpl(FantasyTeamRepository fantasyTeamRepository, GameweekRepository gameweekRepository,
                                  FantasyTeamGameweekRepository fantasyTeamGameweekRepository,
                                  PlayerGameweekRepository playerGameweekRepository, PlayerRepository playerRepository) {
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.gameweekRepository = gameweekRepository;
        this.fantasyTeamGameweekRepository = fantasyTeamGameweekRepository;
        this.playerGameweekRepository = playerGameweekRepository;
        this.playerRepository = playerRepository;
    }

    public List<FantasyTeam> findAll() {
        return fantasyTeamRepository.findAll();
    }

    public FantasyTeam findOne(Long id) {
        return fantasyTeamRepository.findById(id).orElse(null);
    }

    public void save(FantasyTeam fantasyTeam) {
        fantasyTeamRepository.save(fantasyTeam);
    }

    //Eventually validate so the size of the selections is 11 to start, then 15.
    public void addSelections(Long id, List<SelectionDto> selectionDtos) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.getOne(id);
        List<Selection> selections = fantasyTeam.getSelections();

        for (SelectionDto selectionDto : selectionDtos) {
            Selection selection = new Selection(playerRepository.findById(selectionDto.getPlayerId())
                    .orElse(null),selectionDto.isCaptained());
            selections.add(selection);
        }

        fantasyTeam.setSelections(selections);
        fantasyTeamRepository.save(fantasyTeam);
    }

    //Eventually validate so the size of the selections is 11 to start, then 15.
    //not sure if this should take a DTO as a parameter in the service level
    //or if it should only be in the controller level using DTOs
    public void updateSelections(Long id, List<SelectionDto> selectionDtos) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.getOne(id);
        List<Selection> selectionActives = fantasyTeam.getSelections();

        for (int i = 0; i < selectionActives.size(); i++) {
            selectionActives.get(i).setPlayer(playerRepository.findById(
                    selectionDtos.get(i).getPlayerId()).orElse(null));
        }
        fantasyTeamRepository.save(fantasyTeam);
    }

    public List<Selection> getSelections(Long id) {
        return fantasyTeamRepository.findById(id).orElse(null).getSelections();
    }

    public void createNextGameweekForEachTeam() {
        List<FantasyTeam> fantasyTeams = fantasyTeamRepository.findAll();
        //get the next gameweek from the database. only using 173644 for debugging.
        //Gameweek nextGameweek = gameweekRepository.findFirstByStartDateAfter(new Date(System.currentTimeMillis()));
        Long gameweekNum = 173644L;
        Gameweek gameweek = gameweekRepository.getOne(gameweekNum);

        for (FantasyTeam fantasyTeam : fantasyTeams) {
            List<Selection> copyOfSelections = new ArrayList<>(fantasyTeam.getSelections());
            FantasyTeamGameweek fantasyTeamGameweek = new FantasyTeamGameweek(fantasyTeam, gameweek,
                    false, copyOfSelections);
            fantasyTeamGameweekRepository.save(fantasyTeamGameweek);
        }
    }

    public void updateTotalScores() {
        List<FantasyTeamGameweek> fantasyTeamGameweeks = fantasyTeamGameweekRepository.findAll();

        for (FantasyTeamGameweek fantasyTeamGameweek : fantasyTeamGameweeks) {
            FantasyTeam fantasyTeam = fantasyTeamGameweek.getFantasyTeam();
            int totalPoints = fantasyTeam.getTotalPoints();
            for (Selection selection : fantasyTeamGameweek.getSelections()) {
                selection.setPoints(calculateCaptainPoints(selection, fantasyTeamGameweek.getGameweek()));
                totalPoints += selection.getPoints();
            }
            fantasyTeam.setTotalPoints(totalPoints);
            fantasyTeamRepository.save(fantasyTeam);
        }
    }

    private int calculateCaptainPoints(Selection selection, Gameweek gameweek) {
        int total = playerGameweekRepository.findByPlayerIdAndGameweekId(selection.getId(), gameweek.getId()).getPoints();
        if (selection.isCaptained()) {
            total = total * 2;
        }
        return total;
    }
}
