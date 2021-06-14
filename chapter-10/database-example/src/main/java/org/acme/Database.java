package org.acme;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Database {

    @Incoming("database")
    public Uni<Void> write(Person person) {
        return Panache.withTransaction(person::persist)
                .replaceWithVoid();
    }

}
