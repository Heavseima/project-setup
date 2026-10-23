# Attendance module (Owner 2)

From the spec:
- Data is synced from the RDA API (student name, check-in, check-out).
- Filter by class, date (month or specific day) and status (all, on time, late, miss scan, absent). Search by student name.
- ADMIN and INSTRUCTOR can read. Instructors have view-only access (feature matrix).
- At-risk tracking depends on attendance, so publish an event after each sync.

## Module boundary rules
- Other modules may only use `attendance.api` and `attendance.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__attendance_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
