package pl.feature.toggle.service.web.exception;

import pl.feature.toggle.service.web.client.ContextBuilder;

public class ServiceCommunicationException extends RuntimeException {
    public ServiceCommunicationException(String message, ContextBuilder contextBuilder) {
        super(message);
    }
}
