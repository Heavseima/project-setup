# Dashboard module (Owner 2)

From the spec:
- **Read-only.** Never writes and owns no business data. It reads through other modules' `api/`.
- Admin + Instructor: student overview KPIs (total, active, dropped, graduated); gender distribution; top 5 by course score, extra-class hours, IT score and Korean score (filter by generation); students at risk by level; attendance breakdown (present, absent, late, permission) and attendance rate over time.
- Admin only: alumni total and employment status breakdown.
- Reports: export as CSV or PDF by week, month or custom range, with a preview before export.

## Module boundary rules
- Other modules may only use `dashboard.api` and `dashboard.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__dashboard_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
