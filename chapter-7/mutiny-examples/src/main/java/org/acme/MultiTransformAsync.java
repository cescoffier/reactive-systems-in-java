package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@SuppressWarnings("Convert2MethodRef")
public class MultiTransformAsync {

    static Random random = new Random();

    static GreetingService service = name -> Uni.createFrom().item(name)
            .emitOn(Infrastructure.getDefaultExecutor())
            .onItem().call(() -> {
                int delay = random.nextInt(100);
                return Uni.createFrom().voidItem().onItem().delayIt().by(Duration.ofMillis(delay));
            });

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        Multi<String> multi = Multi.createFrom().items("Leia", "Luke");
        multi
                .onItem().transformToUniAndConcatenate(name -> service.greeting(name))
                .subscribe().with(
                    s -> System.out.println("(concatenation) Received: " + s),
                    f -> f.printStackTrace(),
                    () -> latch.countDown()
        );

        multi
                .onItem().transformToUniAndMerge(name -> service.greeting(name))
                .subscribe().with(
                    s -> System.out.println("(merge) Received: " + s),
                    f -> f.printStackTrace(),
                    () -> latch.countDown()
        );

        latch.await();
    }


    interface GreetingService {
        Uni<String> greeting(String name);
    }
}
