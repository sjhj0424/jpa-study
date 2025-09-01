package com.example.jpastudy.api;

import com.example.jpastudy.domain.Member;
import com.example.jpastudy.domain.Team;
import com.example.jpastudy.repository.TeamRepository;
import com.example.jpastudy.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    private final TeamRepository teamRepository;

    public MemberController(MemberService memberService, TeamRepository teamRepository) {
        this.memberService = memberService;
        this.teamRepository = teamRepository;
    }

    // Team endpoints
    public record TeamCreateRequest(String name) {}
    public record TeamDto(Long id, String name, int memberCount) {}

    @PostMapping("/teams")
    public TeamDto createTeam(@RequestBody TeamCreateRequest request) {
        Team team = teamRepository.save(new Team(request.name()));
        return toDto(team);
    }

    @GetMapping("/teams")
    public List<TeamDto> getTeams() {
        return teamRepository.findAll().stream()
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
        Member saved = memberService.createMember(request.name(), request.age(), request.teamId());
        return toDto(saved);
    }

    @GetMapping("/members")
    public List<MemberDto> getMembers() {
        return memberService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        return toDto(memberService.findById(id));
    }

    @PutMapping("/members/{id}")
    public MemberDto updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
        Member updated = memberService.updateMember(id, request.name(), request.age(), request.teamId());
        return toDto(updated);
    }

    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }

    private MemberDto toDto(Member m) {
        Long teamId = m.getTeam() != null ? m.getTeam().getId() : null;
        String teamName = m.getTeam() != null ? m.getTeam().getName() : null;
        return new MemberDto(m.getId(), m.getName(), m.getAge(), teamId, teamName);
    }
}

