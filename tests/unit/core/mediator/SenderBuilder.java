package tests.unit.core.mediator;

package tests.shared;

import core.mediator.Handler;
import core.mediator.Request;
import core.mediator.Sender;
import tests.shared.ApplicationContextMock;

import java.util.HashMap;
import java.util.Map;

/**
 * Test utility to construct a Sender instance with pre-registered handlers.
 * Supports isolated, test-safe scenarios with no Spring context needed.
 */
public class SenderBuilder {

    private final Map<Class<?>, Handler<?, ?>> handlers = new HashMap<>();

    public <TRequest extends Request<TResponse>, TResponse> SenderBuilder withHandler(
            Class<TRequest> requestType,
            Handler<TRequest, TResponse> handler) {
        handlers.put(requestType, handler);
        return this;
    }

    public Sender build() {
        ApplicationContextMock context = new ApplicationContextMock();
        handlers.forEach(context::registerBean);
        return new Sender(context);
    }
}
