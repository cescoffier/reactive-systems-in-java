package org.acme.http;

import io.smallrye.mutiny.Uni;
import org.acme.http.model.Order;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;

@RegisterRestClient(configKey = "validation-service")
public interface ValidationService {

    @POST
    Uni<Void> validate(Order order);
}
