package com.configly.web.client;

import java.util.HashMap;
import java.util.Map;

final class ContextBuilder {

    private static final String SERVICE_ID = "serviceId";
    private static final String URI = "uri";
    private static final String METHOD = "method";
    private static final String CORRELATION_ID = "correlationId";
    private static final String EXCEPTION_TYPE = "exceptionType";
    private static final String EXCEPTION_MESSAGE = "exceptionMessage";
    private static final String STATUS_CODE = "statusCode";

    private final Map<String, Object> context = new HashMap<>();

    static ContextBuilder create() {
        return new ContextBuilder();
    }

    private ContextBuilder with(String key, Object value) {
        context.put(key, value);
        return this;
    }

    ContextBuilder serviceId(String serviceId) {
        return with(SERVICE_ID, serviceId);
    }

    ContextBuilder uri(String uri) {
        return with(URI, uri);
    }

    ContextBuilder method(String method) {
        return with(METHOD, method);
    }

    ContextBuilder correlationId(String correlationId) {
        return with(CORRELATION_ID, correlationId);
    }

    ContextBuilder exceptionType(String exceptionType) {
        return with(EXCEPTION_TYPE, exceptionType);
    }

    ContextBuilder exceptionMessage(String exceptionMessage) {
        return with(EXCEPTION_MESSAGE, exceptionMessage);
    }

    ContextBuilder status(Integer statusCode) {
        return with(STATUS_CODE, statusCode);
    }

    Map<String, Object> build() {
        return Map.copyOf(context);
    }

}
