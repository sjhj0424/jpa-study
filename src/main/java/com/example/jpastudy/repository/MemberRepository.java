package com.example.jpastudy.repository;

import com.example.jpastudy.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

