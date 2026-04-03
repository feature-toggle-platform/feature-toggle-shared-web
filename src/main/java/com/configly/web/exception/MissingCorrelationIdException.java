package com.configly.web.exception;

public class MissingCorrelationIdException extends RuntimeException {
    public MissingCorrelationIdException() {
        super("Cannot forward request, because there is no correlation id.");
    }
}
