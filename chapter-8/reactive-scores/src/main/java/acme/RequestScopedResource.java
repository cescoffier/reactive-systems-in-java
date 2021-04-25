package acme;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/request")
@RequestScoped
public class RequestScopedResource {

    int count = 0;

    @GET
    public String requestScoped() {
        return this + "-" + count++;
    }
}
