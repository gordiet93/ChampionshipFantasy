package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
