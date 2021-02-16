package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.dto.FantasyTeamDto;
import com.example.ChampionshipFantasy.dto.UserDto;
import com.example.ChampionshipFantasy.model.FantasyTeam;
import com.example.ChampionshipFantasy.model.User;
import com.example.ChampionshipFantasy.repository.UserRepository;
import com.example.ChampionshipFantasy.service.FantasyTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private FantasyTeamService fantasyTeamService;

    @Autowired
    public UserController(UserRepository userRepository, FantasyTeamService fantasyTeamService) {
        this.userRepository = userRepository;
        this.fantasyTeamService = fantasyTeamService;
    }

    @GetMapping
    public List<User> findAll() { return userRepository.findAll(); }

    @GetMapping("/{id}")
    public User findOne(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UserDto userDto) {
        userRepository.save(new User(userDto.getName(), userDto.getEmail()));
    }

    //should include selections when creating a fantasy team
    @PostMapping(path = "/{id}/fantasyteam", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody FantasyTeamDto fantasyTeamDto, @PathVariable("id") Long id) {
        fantasyTeamService.save(new FantasyTeam(fantasyTeamDto.getName(), userRepository.getOne(id)));
    }
}
