package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@SuppressWarnings("Convert2MethodRef")
public class UniTransformAsync {

    public static void main(String[] args) {
        Uni<String> uni = Uni.createFrom().item("mutiny!");

        uni
            .onItem().transformToUni(item -> callMyRemoteService(item))
            .subscribe().with(s -> System.out.println("Received: " + s));

        uni
            .onItem().transformToMulti(s -> getAMulti(s))
            .subscribe().with(
                s -> System.out.println("Received item: " + s),
                () -> System.out.println("Done!")
        );
    }

    private static Multi<String> getAMulti(String item) {
        return Multi.createFrom().items(item, item);
    }

    private static Uni<String> callMyRemoteService(String item) {
        return Uni.createFrom().item(item.toUpperCase());
    }
}
