package org.acme;

import io.smallrye.mutiny.Multi;

public class MultiTransform {

    public static void main(String[] args) {
        Multi<String> multi = Multi.createFrom().items("a", "b", "c", "d");
        Multi<String> transformed = multi
                .onItem().transform(String::toUpperCase)
                .onFailure().transform(MyBusinessException::new);

        transformed.subscribe()
                .with(item -> System.out.println(">> " + item));
    }

    private static class MyBusinessException extends Throwable {
        public MyBusinessException(Throwable f) {
        }
    }
}
