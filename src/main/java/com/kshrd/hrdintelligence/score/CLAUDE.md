# Score module (Owner 1)

From the spec:
- Data is synced from the Assessment API.
- View scores by subject. The system calculates the total and the rank.
- ADMIN can set the score weights used in the total.
- Search by student name. Filter by classroom or monthly result.
- ADMIN and INSTRUCTOR can read. Only ADMIN manages weights.

## Module boundary rules
- Other modules may only use `score.api` and `score.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__score_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
