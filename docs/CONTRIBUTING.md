# Contributing

## Branches

- `main` is always releasable and protected. Changes go in through PRs only.
- Feature branches: `feature/<module>-<short-desc>`, e.g. `feature/allowance-monthly-export`
- Fixes: `fix/<module>-<short-desc>` · chores: `chore/<desc>` · docs: `docs/<desc>`

## Commits — [Conventional Commits](https://www.conventionalcommits.org/)

```
<type>(<module>): <summary in imperative mood>

feat(allowance): calculate monthly total per student
fix(attendance): handle missing check-out from RDA sync
test(atrisk): cover threshold boundaries
chore(build): bump springdoc to 2.8.17
```

Types: `feat`, `fix`, `refactor`, `test`, `docs`, `chore`, `perf`.

## Pull request checklist

- [ ] `./gradlew check` passes locally (formatting, tests, `ModularityTests`)
- [ ] Only my module(s) changed, or the other owners are tagged as reviewers
- [ ] New endpoints have `@PreAuthorize` and tests for 401/403
- [ ] New/changed tables come with a **new** Flyway migration that matches the ERD
- [ ] No secrets, `.env` values or tokens in code, logs or tests
- [ ] The module's `CLAUDE.md` is updated if a business rule changed
- [ ] PR description says what changed, why, and how it was tested

At least one approval is required. Changes to `shared/`, `config/` or `build.gradle` need the platform owner (Owner 4).

## How to add things

Each recipe matches a Claude Code skill (see [CLAUDE-CODE.md](CLAUDE-CODE.md)), but you can follow it by hand too.

### A new endpoint (`/build-feature`)
1. Check the ERD for the tables involved.
2. `model/` entity extending `BaseEntity` → migration → `repository/`.
3. `dto/request` + `dto/response` records (validation on requests).
4. `mapper/` MapStruct interface.
5. `service/` interface + `service/impl/` (`@Transactional`); user from `CurrentUser`.
6. `controller/` with `/api/v1/...`, `@PreAuthorize`, `ApiResponse`.
7. Tests, then `./gradlew check`.

### A migration (`/db-migration`)
1. Next number after the highest `V<NNN>` in `src/main/resources/db/migration/`.
2. Name it `V<NNN>__<module>_<description>.sql`.
3. Include the `BaseEntity` columns. No foreign keys to other modules' tables. Add indexes for filters.
4. Never edit a migration that is already merged.

> Two people creating the same version number on different branches will conflict. Whoever merges second
> renumbers their file before merging.

### An event between modules (`/module-event`)
1. Record in `<module>/api/event/`, past-tense name, ids only.
2. `publishEvent(...)` inside the transactional service method.
3. Listener with `@ApplicationModuleListener` in the other module. Make it idempotent.
4. Test with `@ApplicationModuleTest` + `Scenario`.

### Data another module needs
Add a method to your module's `api/` interface (e.g. `StudentApi`) and implement it in your `service/impl`.
Other modules must never use your repository or entity.

## Code style

- Formatting is automatic: `./gradlew spotlessApply` (palantir-java-format). Don't argue about formatting in reviews.
- Records for DTOs. No `@Data` on entities. `BigDecimal` for money. `Instant` for timestamps.
- No commented-out code or leftover `System.out.println`. Use `@Slf4j` and `log.debug/info`.
- Never log tokens, passwords, API keys or personal data.
