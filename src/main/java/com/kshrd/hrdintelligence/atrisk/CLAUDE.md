# At-Risk module (Owner 4)

From the spec:
- Automatically identifies students at risk from low attendance and/or declining academic performance, and generates alerts.
- Risk level: High, Medium, Low. Risk type: attendance, score, attitude.
- Filter by level and type. Sort high→low, low→high, or name A–Z.
- Reads data only through `attendance.api`, `score.api` and `student.api`. Publishes an event when a student is at risk; `notification` handles alerts.
- Thresholds are not defined yet. Make them configurable, not constants.

## Module boundary rules
- Other modules may only use `atrisk.api` and `atrisk.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__atrisk_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
