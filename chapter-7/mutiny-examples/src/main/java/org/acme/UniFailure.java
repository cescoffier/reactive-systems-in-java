package org.acme;

import io.smallrye.mutiny.Uni;

import java.io.IOException;
import java.time.Duration;

public class UniFailure {

    public static void main(String[] args) throws InterruptedException {
        Uni<String> uni = Uni.createFrom().failure(new IOException("boom"));
        uni
            .onFailure().recoverWithItem("hello!")
            .subscribe().with(item -> System.out.println("Recovering with item >> " + item));

        uni
            .onFailure().recoverWithUni(callFallbackService())
            .subscribe().with(item -> System.out.println("Recovering with uni >> " + item));

        uni
            .onFailure().retry().withBackOff(Duration.ofSeconds(1), Duration.ofSeconds(3)).atMost(5)
            .subscribe().with(
                    item -> System.out.println("Recovering with retry >> " + item),
                    failure -> System.out.println("Still unsuccessful " + failure));

        // Use CTRL+C to stop the program.
        Thread.currentThread().join();
    }

    private static Uni<? extends String> callFallbackService() {
        return Uni.createFrom().item("hello")
                // Inject some artificial delay
                .onItem().delayIt().by(Duration.ofMillis(10));
    }
}
