package org.acme.reactive;

import io.smallrye.mutiny.Uni;

public class GreetingExample {

    public static void main(String[] args) {
        GreetingService greetingService = new GreetinServiceImpl();

        greetingService.greeting("Luke")
                .subscribe().with(
                item -> System.out.println(item),
                failure -> System.out.println("Oh no! " + failure.getMessage())
        );

        greetingService.greeting("Luke")
                .onFailure().recoverWithItem("Hello Luke")
                .subscribe().with(
                item -> System.out.println(item)
        );

        greetingService.greeting("Leia")
                .onItem().transformToUni(greetingForLeia -> {
            System.out.println(greetingForLeia);
            return greetingService.greeting("Luke");
        }).subscribe().with(
                    greetingForLuke -> System.out.println(greetingForLuke),
                    failure -> System.out.println("Oh no! " + failure.getMessage())
        );

        Uni<String> leia = greetingService.greeting("Leia");
        Uni<String> luke = greetingService.greeting("Luke");

        Uni.combine().all().unis(leia, luke).asTuple()
                .subscribe().with(
                        tuple -> {
                            System.out.println("Greetings for Leia: " + tuple.getItem1());
                            System.out.println("Greetings for Luke: " + tuple.getItem2());
                        },
                        failure -> System.out.println("Oh no! " + failure.getMessage())
        );
    }

    interface GreetingService {
        Uni<String> greeting(String name);
    }

    static class GreetinServiceImpl implements GreetingService {

        @Override
        public Uni<String> greeting(String name) {
            return Uni.createFrom().item("Hello " + name);
        }
    }

}
