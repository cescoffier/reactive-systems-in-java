package org.acme;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;

public class UniApi {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);
        Uni<HttpResponse<Buffer>> uni = client.getAbs("https://httpbin.org/json").send();
        uni
            .onItem().transform(HttpResponse::bodyAsJsonObject)
            .onFailure().recoverWithItem(new JsonObject().put("message", "fallback"))
            .subscribe().with(
                json -> System.out.println("Got json document: " + json)
        );
    }
}
