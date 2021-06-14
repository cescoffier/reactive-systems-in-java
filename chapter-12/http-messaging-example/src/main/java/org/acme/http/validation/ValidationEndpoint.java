package org.acme.http.validation;

import org.acme.http.model.Order;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/validation")
public class ValidationEndpoint {

    @POST
    public Response validate(Order order) {
        if (order.quantity <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.accepted().build();
    }
}
