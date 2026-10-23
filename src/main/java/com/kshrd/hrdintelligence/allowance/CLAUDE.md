# Allowance module (Owner 1)

From the spec (ADMIN only, except instructors submitting and viewing their own overtime reports):
- Instructor overtime: view records and total hours, auto-calculate the allowance from hours, manage and update it.
- Extra-class: view records and total hours, filter by month, auto-calculate the allowance, view history by student, class or month.
- Monthly allowance types: meal, Korean language, IT score-based, extra-class, class leader, attitude & attendance, other. Types, amounts and rules are editable.
- IT score-based ranges (e.g. ≥90, ≥80) are configurable.
- The system calculates each student's monthly total. Export reports as CSV or Excel.
- Money must be `BigDecimal`, never `double` or `float`.

## Module boundary rules
- Other modules may only use `allowance.api` and `allowance.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__allowance_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
