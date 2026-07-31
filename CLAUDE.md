# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
mvn spring-boot:run          # Run the application (port 8080)
mvn clean install             # Build + run all tests
mvn test                      # Run all tests
mvn test -Dtest=PostRepositoryTest             # Run a single test class
mvn test -Dtest=PostRepositoryTest#whenSavePostThenFindById  # Run a single test method
```

Swagger UI: http://localhost:8080/swagger-ui.html

## Prerequisites

PostgreSQL database must exist before running:
```sql
CREATE DATABASE social_media_api_new;
```
Connection: `localhost:5432/social_media_api_new`, user `postgres`, password `password`.

## Architecture

This is a Spring Boot 3.3.4 REST API for a social network. Java 17, PostgreSQL, Liquibase migrations, JWT auth.

### Two separate user models

The project has two independent user-related entity hierarchies that are **not connected**:

1. **`model.User`** (table `users`) — the business domain user with posts, friends, followers, messages. Used by `UserController`, `PostController`, and all business services.
2. **`security.models.Person`** (table `persons`) — the authentication user with roles. Used exclusively by the security module for login/signup via `AuthController`.

These map to different tables and have no FK relationship. The `security/` subpackage is fully self-contained with its own models, repositories, services, DTOs, and controllers.

### Security flow

- Stateless JWT auth, no sessions. Token passed via `Authorization: Bearer <token>`.
- `AuthTokenFilter` (extends `OncePerRequestFilter`) extracts and validates JWT on every request.
- `AuthController` handles `/api/auth/signup` and `/api/auth/signin`.
- Roles: `ROLE_USER`, `ROLE_ADMIN`, `ROLE_MODERATOR` (enum `ERole`, seeded by migration 007).
- Endpoint authorization rules are in `WebSecurityConfig.filterChain()` — role-based per HTTP method.
- Public endpoints: `/swagger-ui/**`, `/v3/api-docs/**`, `/api/auth/**`, `/api/test/**`.

### Controller patterns

- `UserController` implements `UserControllerInterface` — the interface holds Swagger annotations and `@RequestMapping("/api/user")`, the implementation holds logic.
- `PostController` has annotations directly on the class, mapped to `/api/post`.
- Both use DTOs (`UserDto`, `PostDto`) for request/response, validated with `@Valid`.

### Relationships via composite keys

`Friend` and `Follower` entities use `@EmbeddedId` with compound keys (`FriendKey`, `FollowerKey`) rather than auto-generated IDs. Both reference `User` via `@MapsId`.

### Database migrations

Liquibase changelog at `src/main/resources/db/dbchangelog.xml`. Scripts are numbered sequentially (`001_` through `007_`) in `db/scripts/`. New migrations must be added both as a SQL file and as an `<include>` in `dbchangelog.xml`.

### Tests

Repository integration tests use `@SpringBootTest` with `@ActiveProfiles("test")` and H2 in-memory database. Most test classes are currently commented out.

### Checkstyle

`checkstyle.xml` at project root enforces Java naming conventions, whitespace rules, brace style, and prohibits empty blocks/statements. No explicit Maven plugin configured — run via IDE checkstyle integration.
