package org.acme;

import io.smallrye.mutiny.Multi;

public class MultiObserve {

    public static void main(String[] args) {
        Multi<String> multi = Multi.createFrom().items("a", "b", "c", "d");
        multi
                .onSubscribe().invoke(sub -> System.out.println("Subscribed!"))
                .onCancellation().invoke(() -> System.out.println("Cancelled"))
                .onItem().invoke(s -> System.out.println("Item: " + s))
                .onFailure().invoke(f -> System.out.println("Failure: " + f))
                .onCompletion().invoke(() -> System.out.println("Completed!"))
                .subscribe().with(
                item -> System.out.println("Received: " + item)
        );
    }
}
