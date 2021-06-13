package org.acme;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Path("/")
public class HttpEndpoint {

    @Channel("upload")
    Emitter<Person> emitter;

    @POST
    public Uni<Response> upload(Person person) {
        System.out.println("emitting " + person.name + " / " + emitter.isCancelled());
        return Uni.createFrom().completionStage(() -> {
            CompletableFuture<Void> future = new CompletableFuture<>();
            Message<Person> msg = Message.of(person, () -> {
                System.out.println("Ack " + person.name);
                future.complete(null);
                return CompletableFuture.completedFuture(null);
            }, f -> {
                System.out.println("Nack " + person.name + " " + f.getMessage());
                future.completeExceptionally(f);
                return CompletableFuture.completedFuture(null);
            });
            emitter.send(msg);
            return future;
        })
                .replaceWith(Response.accepted().build())
                .onFailure().recoverWithItem(t -> Response.status(Response.Status.BAD_REQUEST).entity(t.getMessage()).build());
    }

    @GET
    public Uni<List<Person>> getAll() {
        return Person.listAll();
    }

    @POST
    @Path("/post")
    public Uni<Response> post(Person person) {
        return Panache.withTransaction(() -> person.persistAndFlush())
                .replaceWith(Response.accepted().build())
                .onFailure().recoverWithItem(t -> Response.status(Response.Status.BAD_REQUEST).entity(t.getMessage()).build());
    }

}
