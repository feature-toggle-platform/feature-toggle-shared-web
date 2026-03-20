package pl.feature.toggle.service.web.client;

import java.util.HashMap;
import java.util.Map;

public final class ContextBuilder {

    private final Map<String, Object> context = new HashMap<>();

    static ContextBuilder create() {
        return new ContextBuilder();
    }

    ContextBuilder with(String key, Object value) {
        context.put(key, value);
        return this;
    }

    Map<String, Object> build() {
        return Map.copyOf(context);
    }
}
