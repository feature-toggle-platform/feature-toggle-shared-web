package pl.feature.toggle.service.web.client;

import java.util.Map;

public class ServiceCommunicationException extends RuntimeException {
    public ServiceCommunicationException(String message, Map<String, Object> context) {
        super(message + context.toString());
    }
}
