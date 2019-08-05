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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void save(@RequestBody FantasyTeamDto fantasyTeamDto) {
        fantasyTeamRepository.save(dtoToModel(fantasyTeamDto));
    }

    private FantasyTeam dtoToModel(FantasyTeamDto fantasyTeamDto) {
        List<SelectionActive> selectionActives = new ArrayList<>();

        for (SelectionDto selectionDto : fantasyTeamDto.getSelections()) {
            SelectionActive selectionActive = new SelectionActive(
                    playerRepository.getOne(selectionDto.getPlayerId()), selectionDto.isCaptained());

            selectionActives.add(selectionActive);
        }

        return new FantasyTeam(fantasyTeamDto.getName(),
                userRepository.getOne(fantasyTeamDto.getUserId()), selectionActives);
    }
}
