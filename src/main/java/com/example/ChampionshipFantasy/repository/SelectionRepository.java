package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.Selection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionRepository extends JpaRepository<Selection, Long> {
}
