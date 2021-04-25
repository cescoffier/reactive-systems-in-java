package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloEndpoint {

    @GET
    public String hello() {
        return "hello";
    }
}
