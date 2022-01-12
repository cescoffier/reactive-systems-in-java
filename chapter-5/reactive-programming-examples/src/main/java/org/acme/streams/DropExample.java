package org.acme.streams;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import java.time.Duration;

public class DropExample {

    public static void main(String[] args) throws InterruptedException {
        Multi.createFrom().ticks().every(Duration.ofMillis(10))
                .onOverflow().invoke(x -> System.out.println("Dropping item " + x)).drop()
                .emitOn(Infrastructure.getDefaultExecutor())
                .onItem().transform(DropExample::canOnlyConsumeOneItemPerSecond)
                .select().first(10)
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
