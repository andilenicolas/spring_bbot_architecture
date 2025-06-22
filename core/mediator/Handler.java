package com.yourcompany.yourapp.core.mediator;

import java.util.concurrent.CompletableFuture;

/**
 * Contract for handling a specific type of Request.
 *
 * @param <TRequest>  the request type
 * @param <TResponse> the response type
 */
public interface Handler<TRequest extends Request<TResponse>, TResponse> {
    CompletableFuture<TResponse> handle(TRequest request);
}
