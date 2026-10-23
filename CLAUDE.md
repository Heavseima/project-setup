# HRD Intelligence — application-logic-service

Backend for HRD Intelligence (KSHRD final project): manages students, attendance, scores, allowances, evaluations,
at-risk tracking, alumni, documents, notifications and dashboards for HRD admins and instructors.
**Modular monolith**: one Spring Boot app split into Spring Modulith modules. Human docs live in `docs/`.

## Stack
Java 21 · Spring Boot 3.5.16 · Spring Modulith 1.4.14 · Gradle (Groovy DSL) · PostgreSQL 17 · Flyway ·
Spring Security OAuth2 resource server (JWT) · MapStruct · Lombok · springdoc · Testcontainers · Spotless (palantir).

## Commands
- Start dependencies: `docker compose up -d` (Postgres, MinIO, Mailpit)
- Run: `./gradlew bootRun` (profile `dev`, reads `.env`; copy `.env.example`)
- Full gate: `./gradlew check` (Spotless + tests + module boundaries + coverage; needs Docker)
- One test: `./gradlew test --tests '*ModularityTests'`
- Format: `./gradlew spotlessApply` (also runs automatically via hook)

## Modules (`src/main/java/com/kshrd/hrdintelligence/`)
| Module | Owner | Notes |
|---|---|---|
| `student`, `alumni` | Owner 5 | |
| `score`, `allowance` | Owner 1 | |
| `attendance`, `dashboard` | Owner 2 | dashboard is read-only |
| `datamanagement`, `notification` | Owner 3 | notification only listens to events |
| `feedback`, `atrisk`, `integration`, `config`, `shared` | Owner 4 | `shared` is an OPEN module |

Every module has its own `CLAUDE.md` with its rules. Read it before changing that module.

## Architecture rules (enforced by `ModularityTests`)
- Module layout: `controller/`, `service/` (interface) + `service/impl/`, `dto/request/`, `dto/response/`, `model/` (+ `model/enums/`), `mapper/`, `repository/`, and `api/` + `api/event/` for public types.
- Other modules may use **only** `<module>.api` and `<module>.api.event`. Never import another module's `model`, `repository`, `service`, `dto` or `mapper`.
- Reference another module's data by id (`UUID studentId`). No cross-module JPA relations, joins or table writes.
- Cross-module reactions go through events: publish with `ApplicationEventPublisher`, listen with `@ApplicationModuleListener` (see `/module-event`).
- Layer flow: controller → service → repository. Controllers never touch repositories or entities.
- **No AI here.** Chatbot, RAG, embeddings/vectors, OCR, summaries and AI agents belong to the separate AI service. Don't add AI code, dependencies, config or env vars to this project.

## Conventions
- REST paths: `/api/v1/<resource>`. Every endpoint has `@PreAuthorize` (roles `ADMIN`, `INSTRUCTOR`).
- Success body: `ApiResponse<T>`; lists: `ApiResponse<PageResponse<T>>`. Errors: throw `NotFoundException`, `BusinessException` or `ConflictException` from `shared.exception`; `GlobalExceptionHandler` turns them into `ProblemDetail`.
- DTOs are Java records with `jakarta.validation`. Mapping uses MapStruct only.
- Entities extend `shared.model.BaseEntity` (UUID id, audit columns, `@Version`). Enums are stored as `EnumType.STRING`.
- Money: `BigDecimal` / `NUMERIC(12,2)`, never `double`. Times: `Instant` / `TIMESTAMPTZ` (UTC).
- Lombok: only `@Getter`, `@Setter` (entities), `@RequiredArgsConstructor`, `@Builder`, `@Slf4j`. No `@Data` on entities.

## Database
- Schema changes go **only** through Flyway in `src/main/resources/db/migration/` (`ddl-auto=validate`). Use `/db-migration`.
- **Never edit an existing migration.** A hook blocks it.
- Entities, columns and formulas come from the team **ERD**. Don't invent tables or columns. If the ERD doesn't define something, ask.

## Security
- Resource server only: validate JWTs from the external auth server. Never issue tokens, store passwords, or implement login or password flows.
- Get the current user from `shared.security.CurrentUser`. Never trust a user id from the request body.
- Never read, print or commit secrets (`.env`, keys, Firebase JSON). Config uses `${ENV_VAR}` placeholders only.

## Definition of done
`./gradlew check` passes (run `/verify`), new behaviour has tests (including 401/403 for endpoints), and the module's `CLAUDE.md` is updated if a rule changed.

## Workflow
- Use `/plan` first for database, auth, permission or cross-module changes.
- One feature per session. `/clear` between unrelated tasks; `/compact` when the same task gets long.
- Work in parallel with worktrees: `claude -w <branch>`.
- Before a PR: `/review-changes` or the `feature-reviewer` agent, plus `/security-review` for auth or data-access changes.
- Skills: `/new-module`, `/build-feature`, `/db-migration`, `/module-event`, `/review-changes`, `/verify`.

## Where instructions live
| Need | Put it in |
|---|---|
| Something Claude should always know | `CLAUDE.md` (root or module) |
| A repeatable way of doing work | Skill in `.claude/skills/` |
| Something that must always happen | Hook in `.claude/hooks/` + `settings.json` |
| Something Claude must never do | Permissions `deny` in `.claude/settings.json` |
| A focused, separate investigation | Subagent in `.claude/agents/` |
| A personal preference | `CLAUDE.local.md` or `.claude/settings.local.json` (gitignored) |
