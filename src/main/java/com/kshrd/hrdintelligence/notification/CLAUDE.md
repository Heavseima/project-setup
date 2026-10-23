# Notification module (Owner 3)

From the spec:
- Channels: in-app (with unread count), email, and push (FCM) for mobile.
- Triggers: low attendance and declining academics → admin + instructor; alumni profile form submitted → admin; overtime/extra-class reminder → instructor.
- This module **only listens** to events (`@ApplicationModuleListener`). Other modules never call it directly.
- Users only see their own notifications.

## Module boundary rules
- Other modules may only use `notification.api` and `notification.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__notification_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
