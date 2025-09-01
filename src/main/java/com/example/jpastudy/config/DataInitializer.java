package com.example.jpastudy.config;

import com.example.jpastudy.domain.Team;
import com.example.jpastudy.repository.TeamRepository;
import com.example.jpastudy.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initData(TeamRepository teamRepository, MemberService memberService) {
        return args -> {
            Team dev = teamRepository.save(new Team("Dev"));
            Team design = teamRepository.save(new Team("Design"));

            memberService.createMember("Alice", 28, dev.getId());
            memberService.createMember("Bob", 32, dev.getId());
            memberService.createMember("Carol", 25, design.getId());

            log.info("샘플 데이터가 초기화되었습니다.");
        };
    }
}

