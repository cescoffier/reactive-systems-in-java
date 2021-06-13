package org.acme.http;

import io.smallrye.mutiny.Uni;
import org.acme.http.model.Order;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderProcessing {

    @RestClient
    ValidationService validation;

    @Incoming("new-orders")
    @Outgoing("validated-orders")
    Uni<Order> validate(Order order) {
        return validation.validate(order)
                .onItem().transform(x -> order);
    }

}
