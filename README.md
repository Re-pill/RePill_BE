# RePill Backend

## 📖 프로젝트 소개

**RePill**은 의약품 보관/폐기 관리 서비스의 백엔드입니다.
위치 기반 폐의약품 수거처 안내 및 개인별 약품 생명주기, 폐기 기록 등을 관리하며,
Spring Boot 기반의 RESTful API 서버로 구축되었습니다.

---

## 👥 팀 멤버

| 항목 | 최승원 / 레이드 | 이소정                                                             |
|------|--------------------------------------------------------------|-----------------------------------------------------------------|
| GitHub | [Seungwon-Choi](https://github.com/Seungwon-Choi)            | [Sojeong0430](https://github.com/Sojeong0430)                   |
| 프로필 | <img src="https://github.com/Seungwon-Choi.png" width="100"/> | <img src="https://github.com/Sojeong0430.png" width="100"/>     |
| 역할 | Backend                                                      | Backend                                                         |

---

## 🛠 기술 스택

### Core

- **Language**: Java 21
- **Framework**: Spring Boot 3.4.5
- **Build Tool**: Gradle

### Database & Storage

- **RDBMS**: MySQL
- **ORM**: Spring Data JPA
- **Query**: QueryDSL 5.1.0
- **Object Storage**: Azure Blob Storage (Image Upload)

### Security & Auth

- **Security**: Spring Security
- **Authentication**: jjwt 0.11.5
- **OAuth2**: Spring Boot Starter OAuth2 Client

### Documentation

- **API Docs**: SpringDoc OpenAPI 2.7.0 (Swagger UI)

### Testing

- **Test Framework**: JUnit 5, Spring Boot Test

### Etc

- **Lombok**: Boilerplate 제거
- **Validation**: Spring Boot Starter Validation

---

## 📋 Git Convention

### Branch 전략

기본 브랜치

| 브랜치       | 역할               |
|-----------|------------------|
| `main`    | 실제 배포 가능한 안정 브랜치 |
| `develop` | 다음 배포를 위한 통합 브랜치 |

브랜치 네이밍

```
{type}/{issue-number}-{short-description}
```

type

| type     | 설명              |
|----------|-----------------|
| feature  | 신규 기능 개발        |
| bug      | 버그 수정           |
| refactor | 리팩토링 (기능 변경 없음) |
| chore    | 설정, 빌드, 문서 등    |
| hotfix   | 운영 긴급 수정        |

### Commit 메시지 규칙

형식

```
[type]: subject
```

type

| type     | 설명            |
|----------|---------------|
| feat     | 새로운 기능 추가     |
| fix      | 버그 수정         |
| refactor | 리팩터링          |
| docs     | 문서 추가/수정      |
| chore    | 빌드, 설정, 기타 작업 |
| test     | 테스트 코드        |

subject 규칙

- 현재형, 명령문
- 50자 이내
- 마침표 사용 금지

### Pull Request 규칙

PR 제목

```
[type] 이슈 제목
```

규칙

- 백엔드 리뷰어 지정
- approve 1개 이상 시 merge

---

## 📂 프로젝트 구조

프로젝트는 도메인형 디렉토리 구조를 따르며, 각 도메인 하위에 `controller / service / repository / entity / dto / converter` 레이어가 위치합니다.
전역 공통 설정과 시큐리티는 `global` 패키지, 공통 응답/예외 처리는 `apiPayload` 패키지에서 관리합니다.

```
com.repill.backend
├── RePillApplication.java         ← Spring Boot 진입점
│
├── apiPayload                     ← 공통 응답 & 예외 처리
│   ├── code
│   │   └── status                 (성공/실패 상태 코드)
│   └── exception
│       └── handler                (전역 예외 핸들러)
│
├── domain                         ← 도메인별 비즈니스 로직
│   ├── common                     (도메인 공통 모듈)
│   │
│   ├── member                     (회원)
│   │   ├── controller
│   │   ├── service
│   │   ├── repository
│   │   ├── entity
│   │   ├── converter
│   │   └── dto
│   │
│   ├── medicine                   (의약품)
│   │   ├── controller
│   │   ├── service
│   │   ├── repository
│   │   ├── entity
│   │   └── dto
│   │
│   ├── medicineboxarea            (약통 영역)
│   │   ├── controller
│   │   ├── service
│   │   ├── repostory
│   │   ├── entity
│   │   └── dto
│   │
│   ├── discardrecord              (폐기 기록)
│   │   ├── controller
│   │   ├── service
│   │   ├── repository
│   │   ├── entity
│   │   └── dto
│   │
│   └── token                      (인증 토큰)
│       ├── domain
│       ├── repository
│       └── service
│
├── global                         ← 전역 공통 영역
│   ├── configuration              (Bean / 환경 설정)
│   └── security                   (Spring Security)
│       ├── filter                 (JWT 필터)
│       ├── provider               (Authentication Provider)
│       ├── principal              (사용자 Principal)
│       ├── authDTO
│       ├── conveter
│       ├── controller
│       └── handler
│           ├── annotation
│           └── resolver
│
└── test                           ← 헬스 체크 / 샘플 API
    └── dto
```

---
