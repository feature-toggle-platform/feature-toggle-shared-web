package pl.feature.toggle.service.web.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import pl.feature.toggle.service.web.correlation.CorrelationId;
import pl.feature.toggle.service.web.correlation.CorrelationProvider;
import pl.feature.toggle.service.web.exception.ServiceCommunicationException;

@AllArgsConstructor
class SpringInternalRestClient implements InternalRestClient {

    private final RestClient.Builder restClientBuilder;
    private final ServiceRegistryProperties serviceRegistryProperties;
    private final CorrelationProvider correlationProvider;

    @Override
    public <T> T get(ServiceId serviceId, String uri, Class<T> responseClass, Object... uriVariables) {
        try {
            var response = client(serviceId)
                    .get()
                    .uri(uri, uriVariables)
                    .header(CorrelationId.headerName(), correlationId())
                    .retrieve()
                    .body(responseClass);

            return requireBody(response, responseClass, serviceId, uri, HttpMethod.GET);
        } catch (RestClientException ex) {
            throw communicationException(serviceId, uri, HttpMethod.GET, ex);
        }
    }

    @Override
    public <T, B> T post(ServiceId serviceId, String uri, B body, Class<T> responseClass, Object... uriVariables) {
        try {
            var response = client(serviceId)
                    .post()
                    .uri(uri, uriVariables)
                    .header(CorrelationId.headerName(), correlationId())
                    .body(body)
                    .retrieve()
                    .body(responseClass);

            return requireBody(response, responseClass, serviceId, uri, HttpMethod.POST);
        } catch (RestClientException ex) {
            throw communicationException(serviceId, uri, HttpMethod.POST, ex);
        }
    }

    @Override
    public <B> void post(ServiceId serviceId, String uri, B body, Object... uriVariables) {
        post(serviceId, uri, body, Void.class, uriVariables);
    }

    @Override
    public <T, B> T put(ServiceId serviceId, String uri, B body, Class<T> responseClass, Object... uriVariables) {
        try {
            var response = client(serviceId)
                    .put()
                    .uri(uri, uriVariables)
                    .header(CorrelationId.headerName(), correlationId())
                    .body(body)
                    .retrieve()
                    .body(responseClass);

            return requireBody(response, responseClass, serviceId, uri, HttpMethod.PUT);
        } catch (RestClientException ex) {
            throw communicationException(serviceId, uri, HttpMethod.PUT, ex);
        }
    }

    @Override
    public <B> void put(ServiceId serviceId, String uri, B body, Object... uriVariables) {
        put(serviceId, uri, body, Void.class, uriVariables);
    }

    @Override
    public <T> T delete(ServiceId serviceId, String uri, Class<T> responseClass, Object... uriVariables) {
        try {
            var response = client(serviceId)
                    .delete()
                    .uri(uri, uriVariables)
                    .header(CorrelationId.headerName(), correlationId())
                    .retrieve()
                    .body(responseClass);

            return requireBody(response, responseClass, serviceId, uri, HttpMethod.DELETE);
        } catch (RestClientException ex) {
            throw communicationException(serviceId, uri, HttpMethod.DELETE, ex);
        }
    }

    @Override
    public void delete(ServiceId serviceId, String uri, Object... uriVariables) {
        delete(serviceId, uri, Void.class, uriVariables);
    }

    private RestClient client(ServiceId serviceId) {
        return restClientBuilder
                .baseUrl(serviceRegistryProperties.urlFor(serviceId))
                .build();
    }

    private String correlationId() {
        return correlationProvider != null
                ? correlationProvider.current().value()
                : CorrelationId.empty().value();
    }

    private <T> T requireBody(
            T body,
            Class<T> responseClass,
            ServiceId serviceId,
            String uri,
            HttpMethod method
    ) {
        if (responseClass == Void.class) {
            return body;
        }

        if (body == null) {
            throw new ServiceCommunicationException(
                    "Empty response from remote service",
                    context(serviceId, uri, method, null)
            );
        }

        return body;
    }

    private ServiceCommunicationException communicationException(
            ServiceId serviceId,
            String uri,
            HttpMethod method,
            RestClientException ex
    ) {
        return new ServiceCommunicationException(
                "Remote call failed",
                context(serviceId, uri, method, ex)
        );
    }

    private ContextBuilder context(ServiceId serviceId, String uri, HttpMethod method, Exception ex) {
        var contextBuilder = ContextBuilder.create()
                .with("serviceId", serviceId.name())
                .with("uri", uri)
                .with("method", method.name())
                .with("correlationId", correlationId());

        if (ex != null) {
            contextBuilder
                    .with("exceptionType", ex.getClass().getSimpleName())
                    .with("exceptionMessage", ex.getMessage());

            if (ex instanceof HttpStatusCodeException httpException) {
                contextBuilder.with("statusCode", httpException.getStatusCode().value());
            }
        }

        return contextBuilder;
    }
}
