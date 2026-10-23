# HRD Intelligence — Application Logic Service

Backend for **HRD Intelligence**, the KSHRD final project. It manages students, attendance, academic scores,
allowances, instructor evaluations, at-risk tracking, alumni, documents, notifications and dashboards for HRD
admins and instructors.

It is a **modular monolith**: one Spring Boot application split into feature modules whose boundaries are
checked automatically by Spring Modulith.

## Quick start

```bash
cp .env.example .env          # then fill in the values
docker compose up -d          # Postgres, MinIO, Mailpit
./gradlew bootRun             # http://localhost:8080
./gradlew check               # formatting + tests + module boundaries (needs Docker)
```

Swagger UI (dev): http://localhost:8080/swagger-ui.html. Details are in [Getting started](docs/GETTING-STARTED.md).

## Documentation

| Doc | What's in it |
|---|---|
| [Architecture](docs/ARCHITECTURE.md) | Modules, folder layout, boundary rules, events, data sync |
| [Dependencies](docs/DEPENDENCIES.md) | Every library: version, purpose, where it's used |
| [Getting started](docs/GETTING-STARTED.md) | Setup, env vars, running, tokens, troubleshooting |
| [Contributing](docs/CONTRIBUTING.md) | Branches, commits, PR checklist, how to add code |
| [Claude Code](docs/CLAUDE-CODE.md) | Team Claude Code setup: skills, agents, permissions, hooks |

## Module ownership

| Owner | Modules |
|---|---|
| Owner 1 | `score`, `allowance` |
| Owner 2 | `attendance`, `dashboard` |
| Owner 3 | `datamanagement`, `notification` |
| Owner 4 | `feedback`, `atrisk`, `integration`, platform (`config`, `shared`, CI/CD, Docker) |
| Owner 5 | `student`, `alumni` |

## Tech stack

Java 21 · Spring Boot 3.5 · Spring Modulith · Gradle · PostgreSQL · Flyway · Spring Security (JWT resource server) ·
MapStruct · MinIO · Firebase Cloud Messaging · Testcontainers · Docker · Jenkins · Argo CD
