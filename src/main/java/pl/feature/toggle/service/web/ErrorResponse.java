package pl.feature.toggle.service.web;

import lombok.Builder;

import java.time.Instant;

public record ErrorResponse(
        String code,
        String message,
        String correlationId,
        Instant timestamp
) {

    @Builder
    public static ErrorResponse of(String code, String message, String correlationId) {
        return new ErrorResponse(
                code,
                message,
                correlationId,
                Instant.now()
        );
    }
}
