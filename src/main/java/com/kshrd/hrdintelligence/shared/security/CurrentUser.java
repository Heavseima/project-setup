package com.kshrd.hrdintelligence.shared.security;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Who is calling. Reads the validated JWT from the security context. Use this in services instead of passing user
 * ids from controllers or trusting ids sent in request bodies.
 */
@Component
public class CurrentUser {

    /** The {@code sub} claim: the user's stable id in the external auth server. */
    public String id() {
        return jwt().getSubject();
    }

    public Optional<String> email() {
        return Optional.ofNullable(jwt().getClaimAsString("email"));
    }

    public Optional<String> name() {
        return Optional.ofNullable(jwt().getClaimAsString("name"));
    }

    public Set<Role> roles() {
        return authentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring("ROLE_".length()))
                .flatMap(name -> {
                    try {
                        return java.util.stream.Stream.of(Role.valueOf(name));
                    } catch (IllegalArgumentException ignored) {
                        return java.util.stream.Stream.empty();
                    }
                })
                .collect(Collectors.toUnmodifiableSet());
    }

    public boolean hasRole(Role role) {
        return roles().contains(role);
    }

    public boolean isAdmin() {
        return hasRole(Role.ADMIN);
    }

    private Jwt jwt() {
        if (authentication().getPrincipal() instanceof Jwt jwt) {
            return jwt;
        }
        throw new IllegalStateException("No JWT in security context");
    }

    private static Authentication authentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("No authenticated user in security context");
        }
        return authentication;
    }
}
