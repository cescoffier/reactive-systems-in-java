package org.acme.greetings;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/")
public class GreetingService {

    @GET
    public String hello(@QueryParam("name") String name) {
        return "Hello " + name;
    }

}
