package tests.unit.core.mediator;

import core.mediator.Request;
import core.mediator.Sender;
import core.mediator.Handler;
import org.junit.jupiter.api.Test;
import tests.shared.SenderBuilder;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

public class SenderTest {

    @Test
    void should_return_expected_response_when_valid_handler_registered() {
        TestRequest request = new TestRequest("Hello");
        Sender sender = new SenderBuilder()
                .withHandler(TestRequest.class, new TestRequestHandler())
                .build();

        CompletableFuture<String> result = sender.send(request);

        assertEquals("Echo: Hello", result.join());
    }

    @Test
    void should_throw_exception_when_no_handler_registered_for_request() {
        Sender sender = new SenderBuilder().build();
        TestRequest request = new TestRequest("Fail");

        Exception ex = assertThrows(IllegalStateException.class, () -> sender.send(request));
        assertTrue(ex.getMessage().contains("No handler found"));
    }

    @Test
    void should_fail_fast_if_wrong_handler_type_registered() {
        Sender sender = new SenderBuilder()
                .withHandler(WrongRequest.class, new WrongRequestHandler())
                .build();

        assertThrows(ClassCastException.class, () -> {
            sender.send(new WrongRequest("Oops")).join();
        });
    }

    // ==== Test Stubs ====

    record TestRequest(String message) implements Request<String> {
    }

    static class TestRequestHandler implements Handler<TestRequest, String> {
        public CompletableFuture<String> handle(TestRequest request) {
            return CompletableFuture.completedFuture("Echo: " + request.message());
        }
    }

    record WrongRequest(String value) implements Request<Integer> {
    }

    static class WrongRequestHandler implements Handler<WrongRequest, String> {
        public CompletableFuture<String> handle(WrongRequest request) {
            return CompletableFuture.completedFuture("Mismatch!");
        }
    }
}
