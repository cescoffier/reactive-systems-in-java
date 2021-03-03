package org.acme.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NonBlockingServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 9999);
        Selector selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);

        channel.socket().bind(address);
        // Server socket only support ACCEPT
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int available = selector.select(); // wait for events
            if (available == 0) {
                continue;  // Nothing ready yet.
            }

            // We got request ready to be processed.
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    // --  New connection --
                    SocketChannel client = channel.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Client connection accepted: " + client.getLocalAddress());
                } else if (key.isReadable()) {
                    // --  A client sent data ready to be read and we can write --
                    SocketChannel client = (SocketChannel) key.channel();
                    // Read the data assuming the size is sufficient for reading.
                    ByteBuffer payload = ByteBuffer.allocate(256);
                    int size = client.read(payload);
                    if (size == -1 ) { // Handle disconnection
                        System.out.println("Disconnection from " + client.getRemoteAddress());
                        channel.close();
                        key.cancel();
                    } else {
                        String result = new String(payload.array(), StandardCharsets.UTF_8).trim();
                        System.out.println("Received message: " + result);
                        if (result.equals("done")) {
                            client.close();
                        }
                        payload.rewind(); // Echo
                        client.write(payload);
                    }
                }
                // We sure we don't handle it twice.
                iterator.remove();
            }
        }
    }

}

