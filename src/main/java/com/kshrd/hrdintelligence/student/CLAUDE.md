# Student module (Owner 5)

From the spec:
- Data is synced from the Assessment API.
- Profile: name, contact (email, phone), gender, date of birth, generation, classroom, course, enrollment date, university.
- Detail view: personal profile, attendance, score, evaluation, risk & support. Each part comes from its owning module's `api/`.
- Search by name or email. Filter by class and status (active, dropped, graduated, all).
- ADMIN and INSTRUCTOR can read.

## Module boundary rules
- Other modules may only use `student.api` and `student.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__student_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
