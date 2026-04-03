package com.configly.web.correlation;


import com.configly.web.exception.MissingCorrelationIdException;

import java.util.UUID;

public record CorrelationId(
        String value
) {

    private static final String HEADER_NAME = "X-Correlation-ID";

    public static CorrelationId of(String value) {
        if (value == null || value.isBlank()) {
            throw new MissingCorrelationIdException();
        }
        return new CorrelationId(value);
    }

    public static CorrelationId generate() {
        return new CorrelationId(UUID.randomUUID().toString());
    }

    public static String headerName() {
        return HEADER_NAME;
    }

    public static String MDCName() {
        return "correlationId";
    }

    public static CorrelationId empty() {
        return new CorrelationId(null);
    }

}
