package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class CreationWithSubscription {

    public static void main(String[] args) {
        Uni.createFrom().emitter(emitter -> {
            // Called for each subscriber and decide
            // when to emit the item or the failure.
            emitter.complete("hello");
        })
            .subscribe().with(
                item -> System.out.println("Received: " + item),
                failure -> System.out.println("D'oh! " + failure)
        );

        Multi.createFrom().emitter(emitter -> {
            // Called for each subscriber and decide
            // when to emit items, failure or completion.
            emitter.emit("hello")
                    .emit("mutiny")
                    .emit("!");
            emitter.complete();
        })
            .subscribe().with(
                item -> System.out.println("Received: " + item),
                failure -> System.out.println("D'oh! " + failure),
                () -> System.out.println("Done!")
        );
    }
}
