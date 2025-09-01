package com.example.jpastudy.adapter.out.persistence;

import com.example.jpastudy.application.port.out.TeamRepositoryPort;
import com.example.jpastudy.domain.Team;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepositoryAdapter implements TeamRepositoryPort {
    private final TeamJpaRepository teamJpaRepository;

    public TeamRepositoryAdapter(TeamJpaRepository teamJpaRepository) {
        this.teamJpaRepository = teamJpaRepository;
    }

    @Override
    public Team save(Team team) {
        return teamJpaRepository.save(team);
    }

    @Override
    public Optional<Team> findById(Long id) {
        return teamJpaRepository.findById(id);
    }

    @Override
    public List<Team> findAll() {
        return teamJpaRepository.findAll();
    }
}

