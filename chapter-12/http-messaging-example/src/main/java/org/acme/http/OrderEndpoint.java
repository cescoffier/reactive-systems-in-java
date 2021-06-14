package org.acme.http;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.acme.http.model.Order;
import org.eclipse.microprofile.reactive.messaging.Channel;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/order")
public class OrderEndpoint {

    @Channel("new-orders")
    MutinyEmitter<Order> emitter;

    @POST
    public Uni<Response> order(Order order) {
        return emitter.send(order)
                .log()
                .onItem().transform(x -> Response.accepted().build())
                .onFailure().recoverWithItem(Response.status(Response.Status.BAD_REQUEST).build());
    }

    @GET
    public Multi<Order> getAllValidatedOrders() {
        return Order.streamAll();
    }

}
