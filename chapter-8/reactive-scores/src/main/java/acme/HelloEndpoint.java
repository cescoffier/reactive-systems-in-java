package acme;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/scores")
public class HelloEndpoint {

    @GET
    @Path("/simple")
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/simple-blocking")
    @Blocking
    public String helloBlocking() {
        return "hello";
    }

    @GET
    @Path("/uni")
    public Uni<String> helloUni() {
        return Uni.createFrom().item("hello");
    }


    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public User user() {
        return new User("leia");
    }


    @GET
    @Path("/json-uni")
    public Uni<User> userUni() {
        return Uni.createFrom().item(new User("leia"));
    }

    @GET
    @Path("/json-blocking")
    @Blocking
    public User userBlocking() {
        return new User("leia");
    }

    @POST
    @Path("/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String post(User user) {
        System.out.println("User is " + user);
        return user.name;
    }

    @GET
    @Path("/stream")
    public Multi<String> stream() {
        return Multi.createFrom().items("a", "b", "c");
    }

    @GET
    @Path("/stream-json")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<User> users() {
        return Multi.createFrom().items(new User("leia"), new User("luke"));
    }

    @GET
    @Path("/stream-sse")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<User> usersSSE() {
        return Multi.createFrom().items(new User("leia"), new User("luke"));
    }

    public static class User {
        public final String name;

        @JsonCreator
        public User(@JsonProperty("name") String name) {
            this.name = name;
        }
    }


}
