---
name: review-changes
description: Review the current uncommitted changes against the team's architecture and conventions before committing or opening a PR.
---

## Current status
!`git status --short`

## Current diff
!`git diff HEAD`

Review these changes for:
1. **Bugs and regressions**: logic errors, null handling, wrong transaction boundaries, N+1 queries, missing pagination.
2. **Module boundaries**: imports of another module's non-`api` packages, cross-module JPA relations, writes to another module's tables, `notification` calling other modules, `dashboard` writing.
3. **Security**: every endpoint has `@PreAuthorize`; user identity comes from `CurrentUser`; private data (feedback) isn't exposed; no secrets or tokens logged; input validated.
4. **Database**: no edits to existing migrations; entity matches migration; indexes for filters; money as `BigDecimal`/`NUMERIC`.
5. **Conventions**: records for DTOs, MapStruct mappers, `ApiResponse`/`PageResponse`, `ProblemDetail` errors, `/api/v1/...` paths.
6. **Tests**: new logic has tests, including 401/403 cases for new endpoints.
7. **Unnecessary changes**: unrelated edits, dead code, leftover debug output.
8. **Violations of CLAUDE.md** (root and the touched modules' CLAUDE.md).

Report as:
## Critical
## Major
## Minor
## Verification (what was run / should be run)
## Recommendation (ready to commit? yes/no)
