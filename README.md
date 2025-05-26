# 🗓️ Todo 일정 관리 애플리케이션

이 프로젝트는 **사용자 기반 일정 관리 기능**을 제공하는 CRUD 웹 애플리케이션입니다. 
사용자는 회원가입 후 로그인 없이 브라우저 세션을 통해 일정(Todo)을 생성, 조회, 수정, 삭제할 수 있습니다. 
각 Todo는 작성자에 귀속되며, 본인만 수정/삭제할 수 있습니다.

## ✅ 프로젝트 개요

- 사용자(User)는 세션으로 구분되며, 로그인은 생략되었고 내부적으로 식별됩니다.
- 일정(Todo)은 제목과 내용, 작성자 정보를 포함하고 작성자 본인만 수정/삭제 가능합니다.
- 댓글(Comment)을 통한 일정 피드백 기능도 포함됩니다.

## ⚙️ 기술 스택

- Java 17
- Spring Boot 3.x
- Spring Web / Validation / JPA
- MySQL
- Gradle
- HTML

## 📌 주요 기능

- 사용자 등록 및 세션 로그인 처리
- Todo 등록 / 조회 / 수정 / 삭제 (본인만 가능)
- 제목/내용 조건부 검색
- 댓글 등록 / 조회 / 수정 / 삭제 (본인만 가능)
- 프론트엔드: 정적 HTML, JS 기반 페이지 구성

## 🔐 보안 / 인가 처리

- Spring Session 활용 없이 HttpSession으로 사용자 구분
- Todo 및 Comment는 작성자 세션과 일치할 때만 수정/삭제 가능
- `AccessDeniedException`을 커스텀 예외 처리로 응답

## 🧩 테이블 설계

### User Table
| 필드명      | 타입        | 설명               |
|-------------|-------------|--------------------|
| id          | BIGINT (PK) | 사용자 고유 ID     |
| userName    | VARCHAR     | 사용자 이름         |
| email       | VARCHAR     | 이메일              |
| password    | VARCHAR     | 비밀번호 (암호화됨) |

### Todo Table
| 필드명      | 타입        | 설명                    |
|-------------|-------------|-------------------------|
| id          | BIGINT (PK) | 일정 ID                 |
| title       | VARCHAR     | 제목                    |
| contents    | TEXT        | 내용                    |
| user_id     | BIGINT (FK) | 작성자 ID (User 참조)   |

### Comment Table
| 필드명      | 타입        | 설명                    |
|-------------|-------------|-------------------------|
| id          | BIGINT (PK) | 댓글 ID                 |
| contents    | TEXT        | 댓글 본문               |
| todo_id     | BIGINT (FK) | 대상 일정 ID            |
| user_id     | BIGINT (FK) | 작성자 ID               |


## 🎯 예외 처리 설계

- 사용자 또는 일정/댓글 미존재: `404 Not Found`
- 인가되지 않은 접근: `403 Forbidden`
- 유효성 실패: `400 Bad Request`
- 서버 에러: `500 Internal Server Error`
- `@RestControllerAdvice` 전역 핸들러로 처리

## 📦 실행 방법

```bash
./gradlew bootRun
```
또는
```bash
./gradlew build
java -jar build/libs/scheduledeveloped-0.0.1-SNAPSHOT.jar
```

## 🧪 테스트

- Postman 또는 `/static` 하위 HTML 파일로 직접 테스트 가능
- 정적 HTML 파일: `todos.html`, `todo.html`, `todo-edit.html`, `login.html`, `profile.html` 등 존재

## 📘 API 목록 (일부 예시)

| 기능             | 메서드 | URL                          | Request 필드           | Response 필드          | 상태     |
|------------------|--------|------------------------------|-------------------------|--------------------------|----------|
| Todo 목록 조회     | GET    | /api/todos?title&contents    | 없음                    | Todo 목록                 | 200 OK   |
| Todo 단건 조회     | GET    | /api/todos/{id}              | 없음                    | Todo 상세정보             | 200 OK   |
| Todo 등록         | POST   | /api/todos                   | title, contents         | 생성된 Todo               | 200 OK   |
| Todo 수정         | PATCH  | /api/todos/{id}              | title, contents         | 수정된 Todo               | 200 OK   |
| Todo 삭제         | DELETE | /api/todos/{id}              | 없음                    | 없음                      | 200 OK   |
| 댓글 조회          | GET    | /api/todos/{todoId}/comments | 없음                    | 댓글 리스트               | 200 OK   |
| 댓글 등록          | POST   | /api/todos/{todoId}/comments | contents                | 등록된 댓글               | 201 Created |
| 댓글 수정          | PATCH  | /api/todos/{todoId}/comments/{id} | contents         | 수정된 댓글               | 200 OK   |
| 댓글 삭제          | DELETE | /api/todos/{todoId}/comments/{id} | 없음              | 없음                      | 200 OK   |


## 📚 API 문서

API 테스트 및 상세 명세는 아래 Postman 링크에서 확인하실 수 있습니다:  
🔗 [API 명세 바로가기]([https://documenter.getpostman.com/view/xxxxxx](https://documenter.getpostman.com/view/27028554/2sB2qZEhrs))

---

본 프로젝트는 학습 및 기능 구현 목적으로 설계되었으며,
실제 운영 환경에서는 JWT 기반 인증, CSRF 보호 등 추가적인 보안 요소가 필요합니다.



