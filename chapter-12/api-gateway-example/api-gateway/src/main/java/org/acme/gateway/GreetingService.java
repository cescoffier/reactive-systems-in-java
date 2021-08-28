package org.acme.gateway;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@RegisterRestClient(configKey = "greeting-service")
public interface GreetingService {

    @GET
    @Path("/")
    @CircuitBreaker
    @Timeout(2000)
    @Fallback(GreetingFallback.class)
    Uni<String> greeting(@QueryParam("name") String name);

    class GreetingFallback implements FallbackHandler<Uni<String>> {
        @Override
        public Uni<String> handle(ExecutionContext context) {
            return Uni.createFrom().item("Hello fallback");
        }
    }
}
