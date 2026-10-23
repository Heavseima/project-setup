package com.kshrd.hrdintelligence.shared.exception;

/** A request that is well-formed but breaks a business rule (mapped to 422 Unprocessable Entity). */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
