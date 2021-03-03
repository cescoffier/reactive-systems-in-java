package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/welcome")
public class WelcomeResource {
  @GET
  public String welcomeMessage() {
    return "Welcome to Quarkus!";
  }
}
