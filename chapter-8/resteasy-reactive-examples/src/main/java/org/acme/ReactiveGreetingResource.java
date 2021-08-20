package org.acme;

import io.smallrye.common.annotation.NonBlocking;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-resteasy-reactive")
public class ReactiveGreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @NonBlocking
    public String hello() {
        return "Hello RESTEasy Reactive from " + Thread.currentThread().getName();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/blocking")
    public String helloBlocking() {
        return "Hello RESTEasy Reactive from " + Thread.currentThread().getName();
    }
}