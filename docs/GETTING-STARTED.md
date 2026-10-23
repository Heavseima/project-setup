# Getting started

## Prerequisites

- **Docker** with Docker Compose (local database, file storage, mail, and tests)
- **JDK**: any JDK 17+ to launch Gradle. The build itself uses Java 21, downloaded automatically if missing.
- **IntelliJ IDEA** (recommended): enable *annotation processing* (Settings → Build → Compiler → Annotation Processors) for Lombok and MapStruct.

## 1. Configure environment

```bash
cp .env.example .env
```

Fill in `.env`. The docker-compose defaults work for the database, MinIO and mail. You need real values for:

| Variable | Where to get it |
|---|---|
| `JWT_ISSUER_URI`, `JWT_JWK_SET_URI` | From the team running the auth server (realm URL and its certs URL) |
| `RDA_API_URL`, `RDA_API_KEY` | From the mentors / RDA API owner |
| `ASSESSMENT_API_URL`, `ASSESSMENT_API_KEY` | From the mentors / Assessment API owner |
| `FCM_CREDENTIALS_PATH` | Path to the Firebase service-account JSON (**keep it outside the repo**) |

Never commit `.env`. It's in `.gitignore`.

The `dev` profile loads `.env` from the project root automatically (`spring.config.import` in `application-dev.yml`),
so both `./gradlew bootRun` and IntelliJ's run button pick it up. Real environment variables still take precedence.

## 2. Start dependencies

```bash
docker compose up -d
docker compose ps
```

| Service | URL |
|---|---|
| PostgreSQL | `localhost:5432` (db `hrd_intelligence`) |
| MinIO API / console | http://localhost:9000 / http://localhost:9001 |
| Mailpit (catches all emails) | http://localhost:8025 |

## 3. Run the app

```bash
./gradlew bootRun
```

- Health: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui.html

Flyway applies migrations automatically on startup.

## 4. Call the API with a token

All `/api/**` endpoints need an access token from the external auth server.

1. Get a token for a test user from the auth-server team (or via its token endpoint).
2. In Swagger UI, click **Authorize** and paste the token (without `Bearer `).
3. The user needs the role `admin` or `instructor` in the `realm_access.roles` claim.

Paste a token into https://jwt.io to check its `iss`, `aud`, `exp` and roles when something returns 401 or 403.

## 5. Run tests

```bash
./gradlew check        # formatting + all tests + module boundaries + coverage
./gradlew test --tests '*ModularityTests'
```

Tests start their own PostgreSQL with Testcontainers, so Docker must be running. They never call real external APIs.

## Troubleshooting

| Problem | Fix |
|---|---|
| `Could not resolve placeholder 'DB_URL'` | `.env` is missing, or the active profile isn't `dev`. See step 1. |
| `401` on every request | Token missing, expired, or `JWT_ISSUER_URI` doesn't match the token's `iss`. |
| `403` | Token is valid, but the user lacks the role required by `@PreAuthorize`. |
| `Schema-validation: missing column` | Entity and migration disagree. Add a **new** migration; never edit an old one. |
| `Validate failed: Migrations have failed validation` | Someone edited an applied migration. Revert that edit. |
| `ModularityTests` fails | You imported another module's internal package. Use its `api/` instead. |
| `spotlessCheck` fails | Run `./gradlew spotlessApply`. |
| Testcontainers can't start | Docker isn't running, or your user can't access the Docker socket. |
| MapStruct/Lombok errors in IntelliJ only | Enable annotation processing; reload the Gradle project. |
