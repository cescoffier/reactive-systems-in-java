package org.acme.streams;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import java.time.Duration;

public class BufferingExample {

    public static void main(String[] args) throws InterruptedException {
        Multi.createFrom().ticks().every(Duration.ofMillis(10))
                .onOverflow().buffer(250)
                .emitOn(Infrastructure.getDefaultExecutor())
                .onItem().transform(BufferingExample::canOnlyConsumeOneItemPerSecond)
                .subscribe().with(
                    item -> System.out.println("Got item: " + item),
                    failure -> System.out.println("Got failure: " + failure)
        );

        Thread.sleep(1000);
    }

    private static long canOnlyConsumeOneItemPerSecond(long x) {
        try {
            Thread.sleep(1000);
            return x;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return x;
        }
    }
}
