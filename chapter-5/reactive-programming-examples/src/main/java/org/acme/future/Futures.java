package org.acme.future;

import io.smallrye.mutiny.tuples.Tuple2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Futures {

    public static void main(String[] args) {
        GreetingService service = new GreetingService();

        CompletionStage<String> future = service.greeting("Luke");

        service.greeting("Luke")
                .thenApply(response -> response.toUpperCase())
                .thenAccept(greeting -> System.out.println(greeting));

        service.greeting("Luke")
            .thenCompose(greetingForLuke -> {
                return service.greeting("Leia")
                        .thenApply(greetingForLeia ->
                                Tuple2.of(greetingForLuke, greetingForLeia)
                        );
            })
            .thenAccept(tuple ->
                    System.out.println(tuple.getItem1() + " " + tuple.getItem2())
            );

        CompletableFuture<String> luke = service.greeting("Luke").toCompletableFuture();
        CompletableFuture<String> leia = service.greeting("Leia").toCompletableFuture();

        CompletableFuture.allOf(luke, leia)
                .thenAccept(ignored -> {
                    System.out.println(luke.join() + " " + leia.join());
                });

        service.greeting("Leia")
                .exceptionally(exception -> "Hello");
    }


    static class GreetingService {

        CompletionStage<String> greeting(String name) {
            return CompletableFuture.completedFuture("Hello " + name);
        }

    }

}
