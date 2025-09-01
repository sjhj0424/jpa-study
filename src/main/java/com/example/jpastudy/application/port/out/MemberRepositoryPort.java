package com.example.jpastudy.application.port.out;

import com.example.jpastudy.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryPort {
    Member save(Member member);
    Optional<Member> findById(Long id);
    List<Member> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
}

