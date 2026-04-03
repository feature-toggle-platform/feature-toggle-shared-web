package com.configly.web.client;

public interface InternalRestClient {
    <T> T get(ServiceId serviceId, String uri, Class<T> responseClass, Object... uriVariables);

    <T, B> T post(ServiceId serviceId, String uri, B body, Class<T> responseClass, Object... uriVariables);

    <B> void post(ServiceId serviceId, String uri, B body, Object... uriVariables);

    <T, B> T put(ServiceId serviceId, String uri, B body, Class<T> responseClass, Object... uriVariables);

    <B> void put(ServiceId serviceId, String uri, B body, Object... uriVariables);

    <T> T delete(ServiceId serviceId, String uri, Class<T> responseClass, Object... uriVariables);

    void delete(ServiceId serviceId, String uri, Object... uriVariables);
}
