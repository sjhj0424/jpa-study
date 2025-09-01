package com.example.jpastudy.adapter.out.persistence;

import com.example.jpastudy.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}

