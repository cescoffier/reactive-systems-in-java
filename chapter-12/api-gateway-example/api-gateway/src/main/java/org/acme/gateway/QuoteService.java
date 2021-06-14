package org.acme.gateway;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "quote-service")
public interface QuoteService {

    @GET
    @Path("/")
    @CircuitBreaker
    @Timeout(2000)
    @NonBlocking
    @Fallback(fallbackMethod = "getFallbackQuote")
    Uni<String> getQuote();

    default Uni<String> getFallbackQuote() {
        return Uni.createFrom().item("no coffee - no quote");
    }

}
