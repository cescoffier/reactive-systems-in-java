package org.acme;

import io.smallrye.mutiny.Multi;

public class MultiApi {

    public static void main(String[] args) {
        Multi<String> multi = Multi.createFrom().items("a", "b", "c", "d");
        multi
                .select().where(s -> s.length() > 3)
                .onItem().transform(String::toUpperCase)
                .onFailure().recoverWithCompletion()
                .onCompletion().continueWith("!")
                .subscribe().with(
                    item -> System.out.println("Received: " + item)
        );
    }
}
