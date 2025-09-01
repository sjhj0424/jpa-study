package com.example.jpastudy.application.port.in;

import com.example.jpastudy.domain.Team;

import java.util.List;

public interface TeamUseCase {
    Team createTeam(String name);
    List<Team> findAll();
    Team findById(Long id);
}

