# Integration module (Owner 4)

From the spec:
- Clients for external APIs: **RDA API** (attendance, student name, check-in/out) and **Assessment API** (student info, scores, staff, class, course, org chart).
- This module holds only the HTTP clients and their DTOs, exposed through `integration.api`. Owning modules (student, attendance, score) run their own sync and write their own tables. This module never writes to another module's tables.
- URLs and keys come from env vars only. Never log them. Tests use WireMock, never the real APIs.

## Module boundary rules
- Other modules may only use `integration.api` and `integration.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__integration_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
