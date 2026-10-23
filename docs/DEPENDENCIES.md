# Dependencies

Versions not managed by Spring Boot live in [`gradle/libs.versions.toml`](../gradle/libs.versions.toml). Everything
else gets its version from the Spring Boot 3.5.16 BOM. To change a version, edit the catalog only.

## Platform

| Dependency | Version | Purpose |
|---|---|---|
| Java (toolchain) | 21 | Language level. Gradle downloads JDK 21 automatically (foojay plugin) if you don't have it. |
| Gradle | 9.8.1 (wrapper) | Build tool. Always use `./gradlew`, never a system `gradle`. |
| Spring Boot | 3.5.16 | Application framework and dependency BOM. |
| Spring Modulith BOM | 1.4.14 | Versions for the Modulith artifacts below. |

## Runtime

| Dependency | Purpose | Used in |
|---|---|---|
| `spring-boot-starter-web` | REST controllers, JSON (Jackson), embedded Tomcat | all controllers |
| `spring-boot-starter-validation` | `@Valid`, `@NotNull`, `@Size`… on request DTOs | `dto/request` |
| `spring-boot-starter-data-jpa` | JPA/Hibernate entities and Spring Data repositories | `model`, `repository` |
| `postgresql` | PostgreSQL JDBC driver | runtime |
| `flyway-core` + `flyway-database-postgresql` | Versioned SQL migrations, the only way the schema changes | `db/migration` |
| `spring-boot-starter-security` | Security filter chain, `@PreAuthorize` | `config/security`, controllers |
| `spring-boot-starter-oauth2-resource-server` | Validates JWT access tokens from the external auth server | `config/security` |
| `spring-modulith-starter-core` | Module model, `@ApplicationModuleListener`, `@NamedInterface` | `package-info.java`, listeners |
| `spring-modulith-starter-jdbc` | Stores published events in `event_publication` so they survive crashes | events |
| `spring-modulith-actuator` | `/actuator/modulith`: module structure at runtime | ops |
| `spring-boot-starter-actuator` | Health/readiness probes, info, metrics | k8s probes |
| `micrometer-registry-prometheus` | `/actuator/prometheus` metrics endpoint | monitoring |
| `spring-boot-starter-mail` | Sending email (alumni forms, reminders, alerts) | `notification`, `alumni` |
| `minio` (8.6.0) | S3-compatible object storage client for uploaded files | `datamanagement` |
| `firebase-admin` (9.11.0) | Firebase Cloud Messaging push notifications to mobile apps | `notification` |
| `poi-ooxml` (5.5.1) | Excel (`.xlsx`) export | `allowance`, `dashboard` |
| `opencsv` (5.12.0) | CSV export | `allowance`, `dashboard` |
| `springdoc-openapi-starter-webmvc-ui` (2.8.17) | OpenAPI spec + Swagger UI | all controllers |
| `mapstruct` (1.6.3) | Compile-time entity ↔ DTO mapping (`@Mapper`) | `mapper` |
| `lombok` | Getters/setters, constructors, `@Slf4j` | everywhere (limited, see CLAUDE.md) |
| `lombok-mapstruct-binding` (0.2.0) | Makes Lombok and MapStruct annotation processors cooperate | build |
| `spring-boot-configuration-processor` | IDE autocomplete for `app.*` properties | build |
| `spring-boot-devtools` | Auto-restart in dev (not in the production jar) | dev only |

## Test

| Dependency | Purpose |
|---|---|
| `spring-boot-starter-test` | JUnit 5, AssertJ, Mockito, MockMvc |
| `spring-security-test` | `jwt()` request post-processor: fake authenticated users with roles |
| `spring-modulith-starter-test` | `@ApplicationModuleTest`, `Scenario` for event tests, `ApplicationModules.verify()` |
| `spring-boot-testcontainers` + `testcontainers` (`junit-jupiter`, `postgresql`) | Real PostgreSQL in Docker for integration tests (`@ServiceConnection`) |
| `wiremock-standalone` (3.13.2) | Fake RDA/Assessment APIs in tests |

## Build plugins

| Plugin | Purpose |
|---|---|
| `org.springframework.boot` | `bootRun`, `bootJar` |
| `io.spring.dependency-management` (1.1.7) | Applies the Boot and Modulith BOMs |
| `com.diffplug.spotless` (8.10.4) + palantir-java-format (2.102.0) | Code formatting: `spotlessApply` fixes, `spotlessCheck` fails the build |
| `jacoco` | Test coverage report: `build/reports/jacoco/test/html/index.html` |
| `org.gradle.toolchains.foojay-resolver-convention` (1.0.0) | Auto-downloads the Java 21 toolchain |

## Adding a dependency

1. Check whether Spring Boot already manages it. If so, add it without a version.
2. Otherwise add the version to `[versions]` and `[libraries]` in `gradle/libs.versions.toml`, and use it as `libs.xxx`.
3. Add a row to this file explaining why we need it.
4. Mention it in your PR description.
