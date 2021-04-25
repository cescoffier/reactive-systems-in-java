package acme;

import io.smallrye.common.annotation.Blocking;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloEndpoint {

    @GET
    @Blocking
    public String hello() {
        return "hello";
    }
}
