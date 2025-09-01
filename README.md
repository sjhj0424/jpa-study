# JPA Study (Spring Boot + JPA + H2)

간단한 팀(Team) ↔ 회원(Member) 예제로 스프링부트와 JPA 사용법을 보여주는 프로젝트입니다. H2 메모리 DB를 사용하며, 시작 시 샘플 데이터가 자동으로 들어갑니다.

## 기술 스택
- Spring Boot 3.3.2
- Spring Data JPA
- Spring Web (REST)
- H2 Database (in‑memory)
- Gradle (Groovy DSL), Java 17

## 요구사항
- JDK 17+
- Gradle(로컬 설치) 또는 Gradle 지원 IDE(IntelliJ 등)

## 실행 방법
1) 콘솔에서 실행
```bash
# 프로젝트 루트에서
gradle bootRun
```
2) 또는 IDE에서 `JpaStudyApplication`(main class) 실행

애플리케이션이 뜨면:
- H2 콘솔: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:jpastudy`
  - User: `sa`, Password: 빈칸

> 주의: `application.yml`에 `spring.jpa.hibernate.ddl-auto=create-drop`이라 앱 시작 시 스키마 생성, 종료 시 제거됩니다. 학습용 설정입니다.

## 폴더/파일 구조(요지)
```
.
├─ build.gradle
├─ settings.gradle
├─ src
│  ├─ main
│  │  ├─ java/com/example/jpastudy
│  │  │  ├─ JpaStudyApplication.java           # 앱 진입점
│  │  │  ├─ api
│  │  │  │  ├─ MemberController.java           # REST API
│  │  │  │  └─ NotFoundException.java
│  │  │  ├─ config
│  │  │  │  └─ DataInitializer.java            # 샘플 데이터 초기화
│  │  │  ├─ domain
│  │  │  │  ├─ Member.java                     # 회원 엔티티(N)
│  │  │  │  └─ Team.java                       # 팀 엔티티(1)
│  │  │  └─ repository
│  │  │     ├─ MemberRepository.java
│  │  │     └─ TeamRepository.java
│  │  └─ resources/application.yml             # DB/JPA/로그 설정
└─ README.md
```

## 도메인 모델
- Team(1) ↔ Member(N)
  - `Team.members`는 `@OneToMany(mappedBy="team", cascade=ALL, orphanRemoval=true)`
  - `Member.team`은 `@ManyToOne(fetch = LAZY)`

## REST API
- 팀
  - POST `/api/teams` — 팀 생성
    - 요청: `{ "name": "Dev" }`
    - 응답: `{ id, name, memberCount }`
  - GET  `/api/teams` — 전체 조회
    - 응답: `[{ id, name, memberCount }, ...]`

- 회원
  - POST   `/api/members` — 회원 생성
    - 요청: `{ "name": "Alice", "age": 28, "teamId": 1 }`
    - 응답: `{ id, name, age, teamId, teamName }`
  - GET    `/api/members` — 전체 조회
  - GET    `/api/members/{id}` — 단건 조회
  - PUT    `/api/members/{id}` — 수정(부분 업데이트)
    - 요청: `{ "name"?: string, "age"?: number, "teamId"?: number }`
  - DELETE `/api/members/{id}` — 삭제

> 리소스를 찾을 수 없는 경우 404(Not Found) 응답을 반환합니다.

## 동작 개요
- 서비스 계층(`MemberService`)에서 트랜잭션 관리(`@Transactional`)
- 수정은 JPA 변경감지(Dirty Checking)로 반영
- 컨트롤러는 DTO로 응답하여 순환참조/지연로딩 이슈 회피
- `DataInitializer`가 기본 팀(Dev/Design)과 샘플 회원을 삽입

## 설정 요약 (`src/main/resources/application.yml`)
- H2 메모리 DB: `jdbc:h2:mem:jpastudy`
- JPA DDL: `create-drop` (학습용)
- SQL 로그: `org.hibernate.SQL=debug`, 포맷팅 활성화
- H2 콘솔: `/h2-console` 활성화

## 간단 사용 예(HTTP)
```http
POST /api/teams
Content-Type: application/json

{ "name": "QA" }
```
```http
POST /api/members
Content-Type: application/json

{ "name": "John", "age": 27, "teamId": 1 }
```
```http
GET /api/members
```

## 확장 아이디어
- QueryDSL 또는 스펙(Specification)으로 동적 검색 추가
- 엔티티 검증(Bean Validation) 및 예외 처리 공통화
- 통합 테스트(Rest Assured/JUnit) 추가

---
필요하시면 Gradle Wrapper 추가나 간단한 통합 테스트도 구성해 드릴게요.
