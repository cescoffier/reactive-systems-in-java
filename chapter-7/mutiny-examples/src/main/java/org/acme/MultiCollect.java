package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultiCollect {

    public static void main(String[] args) {
        Multi<String> multi = Multi.createFrom().items("a", "b", "c", "d");
        Uni<List<String>> itemsAsList = multi.collect().asList();
        Uni<Map<String, String>> itemsAsMap = multi.collect().asMap(item -> getKeyForItem(item));
        Uni<Long> count = multi.collect().with(Collectors.counting());
    }

    private static String getKeyForItem(String item) {
        return item;
    }
}
