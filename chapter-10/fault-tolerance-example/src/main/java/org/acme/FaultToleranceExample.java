package org.acme;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@ApplicationScoped
public class FaultToleranceExample {

    @Outgoing("ticks")
    public Multi<Long> ticks() {
        return Multi.createFrom().ticks()
                .every(Duration.ofSeconds(1))
                .onOverflow().drop();
    }


    @Incoming("ticks")
    @Outgoing("hello")
    @Retry(maxRetries = 10, delay = 1, delayUnit = ChronoUnit.SECONDS)
    public String hello(long tick) {
        maybeFaulty(); // Randomly throws an exception
        return "Hello - " + tick;
    }

    @Incoming("hello")
    public void print(String msg) {
        System.out.println(msg);
    }

    private final Random random = new Random();

    void maybeFaulty() {
        if (random.nextInt(10) > 7) {
            throw new RuntimeException("boom");
        }
    }

}
