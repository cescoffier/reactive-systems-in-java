package org.acme;

import io.smallrye.mutiny.Uni;
import io.vertx.core.file.FileSystemException;
import io.vertx.mutiny.core.Vertx;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class MutinyExampleResource {

    @Inject
    Vertx vertx;


    @GET
    @Path("/lorem")
    public Uni<String> getLoremIpsum() {
        return vertx.fileSystem().readFile("lorem.txt")
                .onItem().transform(buffer -> buffer.toString("UTF-8"));
    }

    @GET
    @Path("/missing")
    public Uni<String> getMissingFile() {
        return vertx.fileSystem().readFile("oups.txt")
                .onItem().transform(buffer -> buffer.toString("UTF-8"));
    }

    @GET
    @Path("/recover")
    public Uni<String> getMissingFileAndRecover() {
        return vertx.fileSystem().readFile("oups.txt")
                .onItem().transform(buffer -> buffer.toString("UTF-8"))
                .onFailure().recoverWithItem("oups!");
    }

    @GET
    @Path("/404")
    public Uni<Response> get404() {
        return vertx.fileSystem().readFile("oups.txt")
                .onItem().transform(buffer -> buffer.toString("UTF-8"))
                .onItem().transform(content -> Response.ok(content).build())
                .onFailure().recoverWithItem(Response.status(Response.Status.NOT_FOUND).build());
    }


    @ServerExceptionMapper
    public Response mapFileSystemException(FileSystemException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ex.getMessage())
                .build();
    }
}
