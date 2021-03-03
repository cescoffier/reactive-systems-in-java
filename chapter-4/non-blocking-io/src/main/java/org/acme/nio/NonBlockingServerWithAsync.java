package org.acme.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class NonBlockingServerWithAsync {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 9999);
        AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open();

        Attachment attachment = new Attachment();
        attachment.setServerChannel(channel);

        channel.bind(address);
        channel.accept(attachment, new CompletionHandler<>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Attachment att) {
                // After receiving a new connection, the server should call the accept method again and wait for
                // the new connection to come in, otherwise no more connection will be processed.
                attachment.getServer().accept(att, this);

                // Handle the connection
                Attachment connection = new Attachment()
                        .setServerChannel(channel)
                        .setClientChannel(client)
                        .setReadMode(true)
                        .setBuffer(ByteBuffer.allocate(2048));

                client.read(connection.getBuffer(), connection, new ChannelHandler());
            }

            @Override
            public void failed(Throwable exc, Attachment attachment) {
                System.out.println("Unable to handle ACCEPT");
            }
        });

        // To prevent main threads from exiting
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

