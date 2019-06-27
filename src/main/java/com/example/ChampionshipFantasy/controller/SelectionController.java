package com.example.ChampionshipFantasy.controller;

import com.example.ChampionshipFantasy.model.Selection;
import com.example.ChampionshipFantasy.repository.SelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/selections")
public class SelectionController {

    private SelectionRepository selectionRepository;

    @Autowired
    public SelectionController(SelectionRepository selectionRepository) {
        this.selectionRepository = selectionRepository;
    }

    @GetMapping("/{id}")
    public Selection findOne(@PathVariable("id") Long id) {
        Selection selection = selectionRepository.findById(id).orElse(null);
        selectionRepository.save(selection);
        return selection;
    }

}
