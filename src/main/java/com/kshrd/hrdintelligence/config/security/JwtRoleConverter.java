package com.kshrd.hrdintelligence.config.security;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * Turns role names found in the token (e.g. {@code realm_access.roles: ["admin"]}) into Spring authorities
 * ({@code ROLE_ADMIN}). Only roles the application knows about matter; unknown ones are kept but never checked.
 */
public class JwtRoleConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final Collection<String> roleClaims;

    public JwtRoleConverter(Collection<String> roleClaims) {
        this.roleClaims = roleClaims;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        for (String path : roleClaims) {
            for (String role : readStrings(jwt.getClaims(), path)) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase(Locale.ROOT)));
            }
        }
        String name =
                jwt.hasClaim("preferred_username") ? jwt.getClaimAsString("preferred_username") : jwt.getSubject();
        return new JwtAuthenticationToken(jwt, authorities, name);
    }

    private static Collection<String> readStrings(Map<String, Object> claims, String path) {
        Object current = claims;
        for (String part : path.split("\\.")) {
            if (!(current instanceof Map<?, ?> map)) {
                return Set.of();
            }
            current = map.get(part);
        }
        if (current instanceof Collection<?> values) {
            return values.stream().map(String::valueOf).toList();
        }
        if (current instanceof String value) {
            return Set.of(value.split("[,\\s]+"));
        }
        return Set.of();
    }
}
