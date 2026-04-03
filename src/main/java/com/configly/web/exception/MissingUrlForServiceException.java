package com.configly.web.exception;

import com.configly.web.client.ServiceId;

public class MissingUrlForServiceException extends RuntimeException {
    public MissingUrlForServiceException(ServiceId serviceId) {
        super("Missing base URL for service: " + serviceId);
    }
}
