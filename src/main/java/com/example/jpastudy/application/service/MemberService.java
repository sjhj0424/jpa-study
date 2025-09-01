package com.example.jpastudy.application.service;

import com.example.jpastudy.application.exception.NotFoundException;
import com.example.jpastudy.application.port.in.MemberUseCase;
import com.example.jpastudy.application.port.out.MemberRepositoryPort;
import com.example.jpastudy.application.port.out.TeamRepositoryPort;
import com.example.jpastudy.domain.Member;
import com.example.jpastudy.domain.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService implements MemberUseCase {
    private final MemberRepositoryPort memberRepository;
    private final TeamRepositoryPort teamRepository;

    public MemberService(MemberRepositoryPort memberRepository, TeamRepositoryPort teamRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Member createMember(String name, int age, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다: id=" + teamId));
        Member member = new Member(name, age, team);
        return memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다: id=" + id));
    }

    @Override
    public Member updateMember(Long id, String name, Integer age, Long teamId) {
        Member member = findById(id);

        if (name != null) member.setName(name);
        if (age != null) member.setAge(age);
        if (teamId != null) {
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다: id=" + teamId));
            member.setTeam(team);
        }
        return member; // 변경 감지로 업데이트 반영
    }

    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new NotFoundException("회원을 찾을 수 없습니다: id=" + id);
        }
        memberRepository.deleteById(id);
    }
}

