package com.kshrd.hrdintelligence.shared.security;

/** Application roles. Values match the role names in the access token (case-insensitive). */
public enum Role {
    ADMIN,
    INSTRUCTOR;

    public String authority() {
        return "ROLE_" + name();
    }
}
