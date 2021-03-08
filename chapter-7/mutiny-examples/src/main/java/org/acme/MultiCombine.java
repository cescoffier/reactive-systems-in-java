package org.acme;

import io.smallrye.mutiny.Multi;

public class MultiCombine {

    public static void main(String[] args) {
        Multi<String> multi1 = Multi.createFrom().items("a", "b", "c");
        Multi<String> multi2 = Multi.createFrom().items("d", "e", "f");

        Multi<String> combined = Multi.createBy().combining().streams(multi1, multi2).asTuple()
                .onItem().transform(tuple -> {
                    String itemFromTheFirstStream = tuple.getItem1();
                    String itemFromTheSecondStream = tuple.getItem2();

                    return combine(itemFromTheFirstStream, itemFromTheSecondStream);
                });

        combined.subscribe().with(System.out::println);
    }

    private static String combine(String itemFromTheFirstStream, String itemFromTheSecondStream) {
        return itemFromTheFirstStream + " - " + itemFromTheSecondStream;
    }
}
