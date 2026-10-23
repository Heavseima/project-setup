# Shared (open module, Owner 4)

Building blocks every module may import: `BaseEntity`, `ApiResponse`, `PageResponse`, exceptions + `GlobalExceptionHandler`, `CurrentUser`, `Role`.

- **No business logic and no module-specific types here.** If only one module needs it, it belongs in that module.
- Changing anything here affects all modules, so keep it backward compatible and tell the team in the PR.
- New error type: add an exception here and map it in `GlobalExceptionHandler` to a `ProblemDetail` with the right status. Never return stack traces or internal messages.
