package com.kshrd.hrdintelligence.shared.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

/** Standard success envelope for every endpoint. Errors use {@link org.springframework.http.ProblemDetail}. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(String message, T payload, Instant timestamp) {

    public static <T> ApiResponse<T> of(String message, T payload) {
        return new ApiResponse<>(message, payload, Instant.now());
    }

    public static <T> ApiResponse<T> ok(T payload) {
        return of("Success", payload);
    }
}
