package org.acme;

import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.function.Supplier;

public class MyMessage implements Message<String> {

    private final String payload;

    public MyMessage(String payload) {
        this.payload = payload;
    }

    public MyMessage(long l) {
        this(Long.toString(l));
    }

    @Override
    public String getPayload() {
        return payload;
    }

    @Override
    public Supplier<CompletionStage<Void>> getAck() {
        return () -> {
            System.out.println("Acknowledgment for " + payload);
            return CompletableFuture.completedFuture(null);
        };
    }

    @Override
    public Function<Throwable, CompletionStage<Void>> getNack() {
        return reason -> {
            System.out.println("Negative acknowledgment for " + payload + ", the reason is " + reason);
            return CompletableFuture.completedFuture(null);
        };
    }
}
