package org.acme.reactive;

import io.smallrye.mutiny.Multi;

public class StreamsExample {

    public static void main(String[] args) {

        Multi<String> stream = Multi.createFrom().items("a", "b", "c", "d");

        stream
                .subscribe().with(
                    item -> System.out.println("Received an item: " + item),
                    failure -> System.out.println("Oh no! Received a failure: " + failure.getMessage()),
                    () -> System.out.println("Received the completion signal")
        );


        stream
                .onItem().transform(circle -> toSquare(circle))
                .subscribe().with(
                    item -> System.out.println("Received a square: " + item),
                    failure -> System.out.println("Oh no! Received a failure: " + failure.getMessage()),
                    () -> System.out.println("Received the completion signal")
        );

        stream
                .onFailure().recoverWithItem(failure -> getFallbackForFailure(failure))
                .subscribe().with(
                    item -> System.out.println("Received a square: " + item),
                    failure -> System.out.println("Oh no! Received a failure: " + failure.getMessage()),
                    () -> System.out.println("Received the completion signal")
        );

        Multi<String> circles = Multi.createFrom().items("a", "b", "c");
        Multi<String> squares = Multi.createFrom().items("D", "E", "F");

        Multi.createBy().merging().streams(circles, squares)
                .subscribe().with(
                item -> System.out.println("Received a square or circle: " + item),
                failure -> System.out.println("Oh no! Received a failure: " + failure.getMessage()),
                () -> System.out.println("Received the completion signal")
        );
    }

    private static String getFallbackForFailure(Throwable failure) {
        return "Hello";
    }

    private static String toSquare(String circle) {
        return circle;
    }
}
