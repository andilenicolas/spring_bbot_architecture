package tests.unit.core.mediator;

package tests.unit.support;

import org.springframework.context.ApplicationContext;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationContextMock implements ApplicationContext {

    private final Map<Class<?>, Object> beanRegistry = new HashMap<>();

    // Register a single bean
    public <T> void registerBean(Class<T> type, T instance) {
        beanRegistry.put(type, instance);
    }

    // Register multiple beans of the same interface/type
    public <T> void registerBeans(Map<Class<? extends T>, T> beans) {
        beanRegistry.putAll(beans);
    }

    // Return a single bean (like getBean(MyService.class))
    @Override
    public <T> T getBean(Class<T> requiredType) {
        Object bean = beanRegistry.get(requiredType);
        if (bean == null) {
            throw new NoSuchElementException("No bean of type " + requiredType.getName() + " found.");
        }
        return requiredType.cast(bean);
    }

    // Return all beans implementing a type (like getBeansOfType(MyInterface.class))
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> result = new HashMap<>();
        for (Map.Entry<Class<?>, Object> entry : beanRegistry.entrySet()) {
            if (type.isAssignableFrom(entry.getKey())) {
                result.put(entry.getKey().getSimpleName(), type.cast(entry.getValue()));
            }
        }
        return result;
    }

    // --- Unsupported operations for safety ---
    @Override
    public Object getBean(String name) {
        throw unsupported();
    }

    @Override
    public Object getBean(String name, Object... args) {
        throw unsupported();
    }

    @Override
    public boolean containsBean(String name) {
        throw unsupported();
    }

    @Override
    public boolean isSingleton(String name) {
        throw unsupported();
    }

    @Override
    public boolean isPrototype(String name) {
        throw unsupported();
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch) {
        throw unsupported();
    }

    @Override
    public Class<?> getType(String name) {
        throw unsupported();
    }

    @Override
    public String[] getAliases(String name) {
        throw unsupported();
    }

    // Utility
    private UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("This method is not supported by ApplicationContextMock.");
    }

    // Other ApplicationContext methods can be stubbed or ignored as needed
}
