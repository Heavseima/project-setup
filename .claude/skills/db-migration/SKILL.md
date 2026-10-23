---
name: db-migration
description: Create a new Flyway migration safely (correct version number, naming, immutability). Use whenever a table, column, index or constraint changes.
---

Migration needed for: $ARGUMENTS

## Current migrations
!`ls src/main/resources/db/migration/`

## Rules
1. **Never edit an existing migration file.** Applied migrations are checksummed; edits break every database that already ran them. A hook blocks this. To change something, write a new migration.
2. Next version = highest `V<NNN>` above + 1, zero-padded to 3 digits. Name: `V<NNN>__<module>_<short_description>.sql`, e.g. `V007__allowance_add_rate_table.sql`.
3. One module per migration file. Table names are singular snake_case. If two modules could clash, prefix the table with the module name.
4. Every table extending `BaseEntity` needs: `id UUID PRIMARY KEY`, `created_at TIMESTAMPTZ NOT NULL`, `updated_at TIMESTAMPTZ NOT NULL`, `created_by VARCHAR(255)`, `updated_by VARCHAR(255)`, `version BIGINT NOT NULL DEFAULT 0`.
5. Enums are stored as `VARCHAR` with a `CHECK` constraint. Money is `NUMERIC(12,2)`. Timestamps are `TIMESTAMPTZ`.
6. No foreign keys to tables owned by **another** module. Store the id column and add an index. Foreign keys inside the same module are fine.
7. Index every column used in filters, sorts and joins. Use unique constraints for natural keys (e.g. `external_id`).
8. Column names and types must match the entity exactly (`ddl-auto=validate` fails the app otherwise).

## Verify
Run `./gradlew test --tests '*ApplicationSecurityTests'` (boots the app with Testcontainers, runs Flyway, and validates the schema) and report the result.
