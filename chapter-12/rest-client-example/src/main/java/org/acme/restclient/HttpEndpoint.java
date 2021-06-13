package org.acme.restclient;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/api")
public class HttpEndpoint {

    @GET
    public String hello(@QueryParam("name") String name) {
        return "hello " + name;
    }


    // TEST

    @RestClient HttpApi api;

    @Path("/test")
    @GET
    public Uni<String> test() {
        return api.helloUni("world");
    }

}
