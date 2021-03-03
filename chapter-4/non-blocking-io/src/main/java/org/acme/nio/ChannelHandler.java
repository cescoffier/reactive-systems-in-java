package org.acme.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

public class ChannelHandler implements CompletionHandler<Integer, Attachment> {

    @Override
    public void completed(Integer result, Attachment att) {
        if (att.isReadMode()) {
            // Data is ready
            ByteBuffer payload = att.getBuffer();

            String content = new String(payload.array(), StandardCharsets.UTF_8).trim();
            System.out.println("Received message: " + content);

            if (content.equalsIgnoreCase("done")) {
                try {
                    att.getClient().close();
                    return;
                } catch (IOException e) {
                    // Ignore me.
                }
            }

            // Echo
            payload.flip();
            att.setReadMode(false); // Toggle read mode.
            att.getClient().write(payload, att, this);
        } else {
            // Data has been written.
            // We can continue to wait for more data:
            att.setReadMode(true);
            att.getBuffer().clear();
            att.getClient().read(att.getBuffer(), att, this);
            // Or just disconnect.
            // try {
            //    att.getClient().close();
            //} catch (IOException e) {
            // Ignore me.
            //}
        }
    }

    @Override
    public void failed(Throwable t, Attachment att) {
        System.out.println("Disconnected");
    }
}