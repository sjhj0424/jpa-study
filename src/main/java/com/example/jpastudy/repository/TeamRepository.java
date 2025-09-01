package com.example.jpastudy.repository;

import com.example.jpastudy.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}

