# Config (Owner 4)

Application-wide configuration: security, JPA auditing, async/scheduling, OpenAPI.

## Security rules
- This service is a **resource server only**. It validates JWTs from the external auth server (signature, `exp`, issuer, audience) and maps roles. It **never** issues tokens, stores passwords, implements login or forgot/change password, or calls the auth server's admin API.
- Roles are read from the claim paths in `app.security.role-claims` (default `realm_access.roles`) and become `ROLE_ADMIN` / `ROLE_INSTRUCTOR`.
- Protect endpoints with `@PreAuthorize` in controllers. Don't add per-URL role rules in `SecurityConfig`.
- Public endpoints are listed in `app.security.public-paths` per profile. Swagger is public in dev only.
- Use `/security-review` for any change in this folder.
