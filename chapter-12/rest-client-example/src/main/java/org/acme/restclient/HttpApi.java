package org.acme.restclient;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "my-http-endpoint")
public interface HttpApi {

    @GET
    @Path("/")
    String hello(@QueryParam("name") String name);

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    Uni<String> helloUni(@QueryParam("name") String name);

}
