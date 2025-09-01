package com.example.jpastudy.application.port.out;

import com.example.jpastudy.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepositoryPort {
    Team save(Team team);
    Optional<Team> findById(Long id);
    List<Team> findAll();
}

