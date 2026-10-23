package com.kshrd.hrdintelligence.config.security;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bound from {@code app.security.*}.
 *
 * @param roleClaims claim paths that hold role names, dot-separated for nested claims
 *     (e.g. {@code realm_access.roles} for Keycloak realm roles)
 * @param publicPaths request patterns that do not need a token
 * @param corsAllowedOrigins origins allowed to call the API from a browser
 */
@ConfigurationProperties(prefix = "app.security")
public record SecurityProperties(List<String> roleClaims, List<String> publicPaths, List<String> corsAllowedOrigins) {

    public SecurityProperties {
        roleClaims =
                roleClaims == null || roleClaims.isEmpty() ? List.of("realm_access.roles") : List.copyOf(roleClaims);
        publicPaths = publicPaths == null ? List.of() : List.copyOf(publicPaths);
        corsAllowedOrigins = corsAllowedOrigins == null ? List.of() : List.copyOf(corsAllowedOrigins);
    }
}
