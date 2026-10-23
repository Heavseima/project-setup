# Alumni module (Owner 5)

From the spec (ADMIN only):
- Profile: personal info, education, generation, course, salary range, phone, uploaded documents.
- Search by name or email. Filter by generation, employment status (employed, self-employed, studying, unemployed) and sector.
- Employment tracking: current status plus a history of past and current jobs (company, title, industry, location).
- Update requests: send a form link by email, manually or automatically every 6 months. A submitted form updates the profile and notifies admins.
- The public form endpoint is the only unauthenticated endpoint here. Secure it with a single-use, expiring token.

## Module boundary rules
- Other modules may only use `alumni.api` and `alumni.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__alumni_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
