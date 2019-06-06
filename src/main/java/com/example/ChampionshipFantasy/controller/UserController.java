package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.User;
import com.example.ChampionshipFantasy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> findAll() { return userRepository.findAll(); }

    @GetMapping("/{id}")
    public User findOne(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User save(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }
}
