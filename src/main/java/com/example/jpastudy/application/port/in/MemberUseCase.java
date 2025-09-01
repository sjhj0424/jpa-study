package com.example.jpastudy.application.port.in;

import com.example.jpastudy.domain.Member;

import java.util.List;

public interface MemberUseCase {
    Member createMember(String name, int age, Long teamId);
    List<Member> findAll();
    Member findById(Long id);
    Member updateMember(Long id, String name, Integer age, Long teamId);
    void deleteMember(Long id);
}

