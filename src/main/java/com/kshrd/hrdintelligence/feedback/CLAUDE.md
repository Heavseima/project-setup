# Feedback module (Owner 4)

From the spec:
- Instructors submit **private** evaluations of students: behavior, soft skills, hard skills and feedback text.
- Instructors can view, edit and manage evaluations they submitted. Filter by class and course.
- Evaluations are never visible to students. Never expose them on a public or student-facing endpoint.
- The author comes from `CurrentUser`, never from the request body.

## Module boundary rules
- Other modules may only use `feedback.api` and `feedback.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__feedback_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
