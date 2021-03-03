package org.acme.vertx;

import io.vertx.core.Vertx;

public class VertxEchoServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        // Create a TCP server
        vertx.createNetServer()
                // Invoke the given function for each connection
                .connectHandler(socket -> {
                    // Just write the content back
                    socket.handler(buffer -> socket.write(buffer));
                })
                .listen(9999);
    }
}
