package com.example.jpastudy.application.service;

import com.example.jpastudy.application.exception.NotFoundException;
import com.example.jpastudy.application.port.in.TeamUseCase;
import com.example.jpastudy.application.port.out.TeamRepositoryPort;
import com.example.jpastudy.domain.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamService implements TeamUseCase {
    private final TeamRepositoryPort teamRepository;

    public TeamService(TeamRepositoryPort teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional
    public Team createTeam(String name) {
        return teamRepository.save(new Team(name));
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다: id=" + id));
    }
}

