package com.configly.web;

import lombok.Builder;
import com.configly.web.correlation.CorrelationId;

import java.time.Instant;

public record ErrorResponse(
        ErrorCode code,
        String message,
        String correlationId,
        Instant timestamp
) {

    @Builder
    public static ErrorResponse of(ErrorCode code, String message, String correlationId) {
        return new ErrorResponse(
                code,
                message,
                correlationId,
                Instant.now()
        );
    }

    public static ErrorResponse from(ErrorCode errorCode, Exception e, CorrelationId correlationId) {
        return ErrorResponse.of(errorCode, e.getMessage(), correlationId.value());
    }
}
