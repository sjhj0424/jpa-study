package com.example.jpastudy.config;

import com.example.jpastudy.application.port.in.MemberUseCase;
import com.example.jpastudy.application.port.in.TeamUseCase;
import com.example.jpastudy.domain.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initData(TeamUseCase teamUseCase, MemberUseCase memberUseCase) {
        return args -> {
            Team dev = teamUseCase.createTeam("Dev");
            Team design = teamUseCase.createTeam("Design");

            memberUseCase.createMember("Alice", 28, dev.getId());
            memberUseCase.createMember("Bob", 32, dev.getId());
            memberUseCase.createMember("Carol", 25, design.getId());

            log.info("샘플 데이터가 초기화되었습니다.");
        };
    }
}
