package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.dto.FantasyTeamDto;
import com.example.ChampionshipFantasy.dto.SelectionDto;
import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.model.SelectionActive;
import com.example.ChampionshipFantasy.repository.FantasyTeamRepository;
import com.example.ChampionshipFantasy.repository.PlayerRepository;
import com.example.ChampionshipFantasy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fantasyteams")
public class FantasyTeamController {

    private FantasyTeamRepository fantasyTeamRepository;
    private UserRepository userRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public FantasyTeamController(FantasyTeamRepository fantasyTeamRepository, UserRepository userRepository,
                                 PlayerRepository playerRepository) {
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
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
    public List<SelectionActive> GetSelections(@PathVariable("id") Long id) {
        return fantasyTeamRepository.findById(id).orElse(null).getSelections();
    }

    //Eventually validate so the size of the selections is 11 to start, then 15.
    @PutMapping("/{id}/selections")
    public void updateSelections(@PathVariable("id") Long id, @RequestBody List<SelectionDto> selectionDtos) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.getOne(id);
        List<SelectionActive> selectionActives = fantasyTeam.getSelections();

        for (int i = 0; i < selectionActives.size(); i++) {
            selectionActives.get(i).setPlayer(playerRepository.getOne(selectionDtos.get(i).getPlayerId()));
        }

        fantasyTeamRepository.save(fantasyTeam);
    }

    //Eventually validate so the size of the selections is 11 to start, then 15.
    @PostMapping("/{id}/selections")
    public void addSelections(@PathVariable("id") Long id, @RequestBody List<SelectionDto> selectionDtos) {
        FantasyTeam fantasyTeam = fantasyTeamRepository.getOne(id);
        List<SelectionActive> selectionActives = fantasyTeam.getSelections();

        for (SelectionDto selectionDto : selectionDtos) {
            SelectionActive selectionActive = new SelectionActive(
                    playerRepository.getOne(selectionDto.getPlayerId()), selectionDto.isCaptained());
            selectionActive.setFantasyTeam(fantasyTeam);

            selectionActives.add(selectionActive);
        }

        fantasyTeam.setSelections(selectionActives);
        fantasyTeamRepository.save(fantasyTeam);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void save(@RequestBody FantasyTeamDto fantasyTeamDto) {
        fantasyTeamRepository.save(new FantasyTeam(fantasyTeamDto.getName(),
                userRepository.getOne(fantasyTeamDto.getUserId())));
    }
}
