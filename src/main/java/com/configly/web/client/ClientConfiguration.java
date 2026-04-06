package com.configly.web.client;

import com.configly.web.model.correlation.CorrelationProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@ConditionalOnClass({RestClient.class})
@EnableConfigurationProperties(ServiceRegistryProperties.class)
class ClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    InternalRestClient internalRestClient(ServiceRegistryProperties serviceRegistryProperties,
                                          ObjectProvider<CorrelationProvider> correlationProviderProvider) {
        return new SpringInternalRestClient(RestClient.builder(), serviceRegistryProperties, correlationProviderProvider.getIfAvailable());
    }
}
