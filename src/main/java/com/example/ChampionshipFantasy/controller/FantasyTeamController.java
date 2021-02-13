package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.dto.FantasyTeamDto;
import com.example.ChampionshipFantasy.dto.SelectionDto;
import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.model.Gameweek;
import com.example.ChampionshipFantasy.model.Selection;
import com.example.ChampionshipFantasy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/fantasyteams")
public class FantasyTeamController {

    private FantasyTeamRepository fantasyTeamRepository;
    private UserRepository userRepository;
    private PlayerRepository playerRepository;
    private GameweekRepository gameweekRepository;
    private PlayerGameweekRepository playerGameweekRepository;

    @Autowired
    public FantasyTeamController(FantasyTeamRepository fantasyTeamRepository, UserRepository userRepository,
                                 PlayerRepository playerRepository, GameweekRepository gameweekRepository,
                                 PlayerGameweekRepository playerGameweekRepository) {
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.gameweekRepository = gameweekRepository;
        this.playerGameweekRepository = playerGameweekRepository;
    }

    @GetMapping
    public List<FantasyTeam> findAll() {
        return fantasyTeamRepository.findAll();
    }

    @GetMapping("/{id}")
    public FantasyTeam FindOne(@PathVariable("id") Long id) {
        return fantasyTeamRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/selections")
    public List<Selection> GetSelections(@PathVariable("id") Long id) {
        return fantasyTeamRepository.findById(id).orElse(null).getSelections();
    }

    //Eventually validate so the size of the selections is 11 to start, then 15.
    @PutMapping("/{id}/selections")
    public void updateSelections(@PathVariable("id") Long id, @RequestBody List<SelectionDto> selectionDtos) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.getOne(id);
        List<Selection> selectionActives = fantasyTeam.getSelections();
        //Gameweek currentGameweek = gameweekRepository.findFirstByStartDateAfter(new Date(System.currentTimeMillis()));
        //for debugging, only get the gameweek that is used in the live data json file
        Long gameweek = 173644L;
        Gameweek currentGameweek = gameweekRepository.getOne(gameweek);

        for (int i = 0; i < selectionActives.size(); i++) {
            selectionActives.get(i).setPlayerGameweek(playerGameweekRepository.findByPlayerIdAndGameweekId(
                    selectionDtos.get(i).getPlayerId(), currentGameweek.getId()));
        }

        fantasyTeamRepository.save(fantasyTeam);
    }

    //Eventually validate so the size of the selections is 11 to start, then 15.
    @PostMapping("/{id}/selections")
    public void addSelections(@PathVariable("id") Long id, @RequestBody List<SelectionDto> selectionDtos) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.getOne(id);
        List<Selection> selections = fantasyTeam.getSelections();

        //Gameweek currentGameweek = gameweekRepository.findFirstByStartDateAfter(new Date(System.currentTimeMillis()));
        Long gameweek = 173644L;
        Gameweek currentGameweek = gameweekRepository.getOne(gameweek);

        for (SelectionDto selectionDto : selectionDtos) {
            Selection selection = new Selection(playerGameweekRepository.findByPlayerIdAndGameweekId(
                    selectionDto.getPlayerId(), currentGameweek.getId()),selectionDto.isCaptained());
            selections.add(selection);
        }

        fantasyTeam.setSelections(selections);
        fantasyTeamRepository.save(fantasyTeam);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void save(@RequestBody FantasyTeamDto fantasyTeamDto) {
        fantasyTeamRepository.save(new FantasyTeam(fantasyTeamDto.getName(),
                userRepository.getOne(fantasyTeamDto.getUserId())));
    }
}
