---
name: new-module
description: Scaffold a new Spring Modulith module with the team's MVC layout, public api/ package, CLAUDE.md and boundary check. Use when adding a new feature module under com.kshrd.hrdintelligence.
---

Create the module: $ARGUMENTS

## Before
1. Read the root `CLAUDE.md` and an existing module (e.g. `src/main/java/com/kshrd/hrdintelligence/student/`) to copy its exact layout.
2. Confirm the module name is a single lowercase word (package name). Check it doesn't overlap an existing module's responsibility. If it does, stop and ask.

## Create
Under `src/main/java/com/kshrd/hrdintelligence/<module>/`:
- `package-info.java` with `@ApplicationModule(displayName = "<Display Name>")` and a one-line Javadoc
- `api/package-info.java` with `@NamedInterface("api")`
- `api/event/package-info.java` with `@NamedInterface("events")`
- empty layer folders with `.gitkeep`: `controller/`, `service/impl/`, `dto/request/`, `dto/response/`, `model/enums/`, `mapper/`, `repository/`
- `CLAUDE.md`: owner, purpose, rules from the spec (only what is actually decided), the module boundary rules section, and "Not decided yet" items

Do not create entities, endpoints or migrations unless the user asked for them and the ERD defines them.

## Finish
1. Run `./gradlew test --tests '*ModularityTests'` and confirm it passes.
2. Add the module to the module table in the root `CLAUDE.md` and in `docs/ARCHITECTURE.md`.
3. Report the files created.
