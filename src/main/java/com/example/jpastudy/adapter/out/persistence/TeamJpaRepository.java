package com.example.jpastudy.adapter.out.persistence;

import com.example.jpastudy.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {
}

