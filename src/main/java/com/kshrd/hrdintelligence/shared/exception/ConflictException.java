package com.kshrd.hrdintelligence.shared.exception;

/** The request conflicts with the current state, e.g. a duplicate unique value (mapped to 409 Conflict). */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
