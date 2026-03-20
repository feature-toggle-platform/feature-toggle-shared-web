package pl.feature.toggle.service.web.exception;

import pl.feature.toggle.service.web.client.ServiceId;

public class MissingUrlForServiceException extends RuntimeException {
    public MissingUrlForServiceException(ServiceId serviceId) {
        super("Missing base URL for service: " + serviceId);
    }
}
