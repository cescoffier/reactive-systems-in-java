package acme;

import io.smallrye.common.annotation.NonBlocking;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloEndpoint {

    @GET
    @NonBlocking
    public String hello() {
        return "hello";
    }
}
