package org.acme.nio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

public class Attachment {

        private AsynchronousServerSocketChannel server;
        private AsynchronousSocketChannel client;
        private boolean isReadMode;
        private ByteBuffer buffer;

        public AsynchronousServerSocketChannel getServer() {
            return server;
        }

        public Attachment setServerChannel(AsynchronousServerSocketChannel server) {
            this.server = server;
            return this;
        }

        public AsynchronousSocketChannel getClient() {
            return client;
        }

        public Attachment setClientChannel(AsynchronousSocketChannel client) {
            this.client = client;
            return this;
        }

        public boolean isReadMode() {
            return isReadMode;
        }

        public Attachment setReadMode(boolean readMode) {
            isReadMode = readMode;
            return this;
        }

        public ByteBuffer getBuffer() {
            return buffer;
        }

        public Attachment setBuffer(ByteBuffer buffer) {
            this.buffer = buffer;
            return this;
        }
    }