package org.acme;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.inject.Singleton;
import java.net.InetAddress;

@Singleton
public class Processor {
  @Incoming("ticks")
  @Outgoing("processed")
  Message<String> process(Message<Long> receivedMessage) throws Exception {
    String value = String.valueOf(receivedMessage.getPayload());
    value += " consumed in pod (" + InetAddress.getLocalHost().getHostName() + ")";

    return receivedMessage.withPayload(value);
  }
}
