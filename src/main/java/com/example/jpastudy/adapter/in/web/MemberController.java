package com.example.jpastudy.adapter.in.web;

import com.example.jpastudy.application.port.in.MemberUseCase;
import com.example.jpastudy.application.port.in.TeamUseCase;
import com.example.jpastudy.domain.Member;
import com.example.jpastudy.domain.Team;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberUseCase memberUseCase;
    private final TeamUseCase teamUseCase;

    public MemberController(MemberUseCase memberUseCase, TeamUseCase teamUseCase) {
        this.memberUseCase = memberUseCase;
        this.teamUseCase = teamUseCase;
    }

    // Team endpoints
    public record TeamCreateRequest(String name) {}
    public record TeamDto(Long id, String name, int memberCount) {}

    @PostMapping("/teams")
    public TeamDto createTeam(@RequestBody TeamCreateRequest request) {
        Team team = teamUseCase.createTeam(request.name());
        return toDto(team);
    }

    @GetMapping("/teams")
    public List<TeamDto> getTeams() {
        return teamUseCase.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TeamDto toDto(Team team) {
        int count = team.getMembers() == null ? 0 : team.getMembers().size();
        return new TeamDto(team.getId(), team.getName(), count);
    }

    // Member endpoints
    public record MemberCreateRequest(String name, int age, Long teamId) {}
    public record MemberUpdateRequest(String name, Integer age, Long teamId) {}
    public record MemberDto(Long id, String name, int age, Long teamId, String teamName) {}

    @PostMapping("/members")
    public MemberDto createMember(@RequestBody MemberCreateRequest request) {
        Member saved = memberUseCase.createMember(request.name(), request.age(), request.teamId());
        return toDto(saved);
    }

    @GetMapping("/members")
    public List<MemberDto> getMembers() {
        return memberUseCase.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        return toDto(memberUseCase.findById(id));
    }

    @PutMapping("/members/{id}")
    public MemberDto updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
        Member updated = memberUseCase.updateMember(id, request.name(), request.age(), request.teamId());
        return toDto(updated);
    }

    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberUseCase.deleteMember(id);
    }

    private MemberDto toDto(Member m) {
        Long teamId = m.getTeam() != null ? m.getTeam().getId() : null;
        String teamName = m.getTeam() != null ? m.getTeam().getName() : null;
        return new MemberDto(m.getId(), m.getName(), m.getAge(), teamId, teamName);
    }
}

