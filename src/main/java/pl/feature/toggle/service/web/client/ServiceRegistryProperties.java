package pl.feature.toggle.service.web.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import pl.feature.toggle.service.web.exception.MissingUrlForServiceException;

import java.util.EnumMap;
import java.util.Map;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class ServiceRegistryProperties {

    private Map<ServiceId, String> services = new EnumMap<>(ServiceId.class);

    public String urlFor(ServiceId serviceId) {
        var url = services.get(serviceId);
        if (url == null || url.isBlank()) {
            throw new MissingUrlForServiceException(serviceId);
        }
        return url;
    }
}
