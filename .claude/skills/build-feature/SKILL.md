---
name: build-feature
description: Implement a feature (endpoint, service logic, entity) inside one module following the team's MVC layers, module boundaries and conventions. Use for any new or changed API behaviour.
---

Build: $ARGUMENTS

## Before implementing
1. Identify the owning module. Read its `CLAUDE.md` and the root `CLAUDE.md`.
2. Check the ERD in `docs/` for the tables and columns involved. If the ERD doesn't define them, **stop and ask**. Don't invent columns.
3. Look at how similar code is already written in this module or another one, and reuse `shared/` (`ApiResponse`, `PageResponse`, `NotFoundException`, `BusinessException`, `ConflictException`, `CurrentUser`).
4. If the feature needs data from another module, use that module's `api/` interface or event. If that API is missing, add a method to the other module's `api/` (tell the user, since another person owns it).

## During implementation (layer order)
1. `model/`: JPA entity extending `BaseEntity`, enums in `model/enums/` stored as `EnumType.STRING`.
2. Flyway migration via `/db-migration` (never `ddl-auto` changes).
3. `repository/`: `JpaRepository` (+ `JpaSpecificationExecutor` for filters).
4. `dto/request` + `dto/response`: Java records; request records carry `jakarta.validation` annotations.
5. `mapper/`: MapStruct interface `@Mapper` (Spring component model is set globally).
6. `service/` interface + `service/impl/` class: `@Transactional(readOnly = true)` on the class, `@Transactional` on writes. Business rules live here. The user comes from `CurrentUser`, never from the request body.
7. `controller/`: `/api/v1/<resource>`, `@PreAuthorize` on every method, `@Valid` on bodies, returns `ApiResponse<…>`, list endpoints return `PageResponse` with `@ParameterObject Pageable`. `@Tag` and `@Operation` for Swagger.
8. Public cross-module types go in `api/`; events go in `api/event/` (use `/module-event`).

## Before finishing
1. Write tests: service unit tests with Mockito, plus a `@SpringBootTest` or `@ApplicationModuleTest` with Testcontainers for repository/endpoint behaviour, including 401/403 cases.
2. Run `/verify`.
3. Report: files changed, endpoints added (method, path, roles), migration added, and anything left undecided.
