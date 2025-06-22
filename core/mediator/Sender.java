package com.yourcompany.yourapp.core.mediator;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mediator dispatcher that maps requests to handlers.
 */
@Component
public class Sender {

    private final ApplicationContext context;
    private final Map<Class<?>, Handler<?, ?>> handlerCache = new ConcurrentHashMap<>();

    @Autowired
    public Sender(ApplicationContext context) {
        this.context = context;
    }

    public <TRequest extends Request<TResponse>, TResponse> CompletableFuture<TResponse> send(TRequest request) {
        Objects.requireNonNull(request, "Request cannot be null");
        Class<?> requestType = request.getClass();

        Handler<TRequest, TResponse> handler = getOrResolveHandler(requestType);
        return handler.handle(request);
    }

    @SuppressWarnings("unchecked")
    private <TRequest extends Request<TResponse>, TResponse> Handler<TRequest, TResponse> getOrResolveHandler(
            Class<?> requestType) {
        return (Handler<TRequest, TResponse>) handlerCache.computeIfAbsent(requestType, this::resolveHandlerFor);
    }

    private Handler<?, ?> resolveHandlerFor(Class<?> requestType) {
        return findMatchingHandlerBean(requestType)
                .orElseThrow(() -> new IllegalStateException(
                        "No matching handler found for request: " + requestType.getName()));
    }

    private Optional<Handler<?, ?>> findMatchingHandlerBean(Class<?> requestType) {
        return context.getBeansOfType(Handler.class).values().stream()
                .filter(handler -> isHandlerForRequest(handler, requestType))
                .findFirst();
    }

    private boolean isHandlerForRequest(Handler<?, ?> handler, Class<?> requestType) {
        for (var type : handler.getClass().getGenericInterfaces()) {
            if (!(type instanceof java.lang.reflect.ParameterizedType paramType))
                continue;
            if (!Handler.class.equals(paramType.getRawType()))
                continue;

            var actualTypeArgs = paramType.getActualTypeArguments();
            if (actualTypeArgs.length == 2 && actualTypeArgs[0] instanceof Class<?> actualRequestType) {
                return requestType.equals(actualRequestType);
            }
        }
        return false;
    }
}
