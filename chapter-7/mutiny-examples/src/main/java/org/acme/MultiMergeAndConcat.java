package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.time.Duration;
import java.util.Random;

public class MultiMergeAndConcat {

    public static void main(String[] args) {
        // Create streams an introduce artificial delays between each items:
        Random random = new Random();
        Multi<String> multi1 = Multi.createFrom().items("a", "b", "c")
                .onItem().call(() ->
                    Uni.createFrom().voidItem().onItem().delayIt().by(Duration.ofMillis(random.nextInt(100)))
                );
        Multi<String> multi2 = Multi.createFrom().items("d", "e", "f")
                .onItem().call(() ->
                        Uni.createFrom().voidItem().onItem().delayIt().by(Duration.ofMillis(random.nextInt(100)))
                );

        Multi<String> concatenated = Multi.createBy().concatenating().streams(multi1, multi2);
        Multi<String> merged = Multi.createBy().merging().streams(multi1, multi2);

        concatenated.subscribe().with(item -> System.out.println("(concatenation) >> " + item));
        merged.subscribe().with(item -> System.out.println("(merge) >> " + item));
    }
}
