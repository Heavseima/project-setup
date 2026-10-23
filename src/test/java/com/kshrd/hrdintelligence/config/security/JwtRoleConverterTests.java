package com.kshrd.hrdintelligence.config.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

class JwtRoleConverterTests {

    private final JwtRoleConverter converter = new JwtRoleConverter(List.of("realm_access.roles"));

    @Test
    void mapsNestedRealmRolesToUppercaseAuthorities() {
        Jwt jwt = jwt(Map.of("realm_access", Map.of("roles", List.of("admin", "instructor"))));

        assertThat(converter.convert(jwt).getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .containsExactlyInAnyOrder("ROLE_ADMIN", "ROLE_INSTRUCTOR");
    }

    @Test
    void missingClaimGivesNoAuthorities() {
        assertThat(converter.convert(jwt(Map.of())).getAuthorities()).isEmpty();
    }

    private static Jwt jwt(Map<String, Object> claims) {
        Jwt.Builder builder = Jwt.withTokenValue("token")
                .header("alg", "none")
                .subject("user-1")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60));
        claims.forEach(builder::claim);
        return builder.build();
    }
}
