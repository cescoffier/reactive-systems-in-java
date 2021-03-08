package org.acme;

import io.smallrye.mutiny.Uni;

public class UniCombine {

    public static void main(String[] args) {
        Uni<String> uni1 = callFirstService();
        Uni<String> uni2 = callSecondService();

        Uni.combine().all().unis(uni1, uni2).asTuple()
                .onItem().transform(tuple -> {
            String responseFromFirstService = tuple.getItem1();
            String responseFromSecondService = tuple.getItem2();

            return combine(responseFromFirstService, responseFromSecondService);
        })
                .subscribe().with(System.out::println);

    }

    private static Uni<String> callFirstService() {
        return Uni.createFrom().item("hello");
    }

    private static Uni<String> callSecondService() {
        return Uni.createFrom().item("world");
    }

    private static String combine(String itemFromTheFirstStream, String itemFromTheSecondStream) {
        return itemFromTheFirstStream + " - " + itemFromTheSecondStream;
    }
}
