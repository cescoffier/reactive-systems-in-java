package org.acme;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.inject.Singleton;
import java.net.InetAddress;

@Singleton
public class Processor {

  private int count = 1;

  @Incoming("ticks")
  @Outgoing("processed")
  @Acknowledgment(Acknowledgment.Strategy.MANUAL)
  Message<String> process(Message<Long> message) throws Exception {
    if (count++ % 8 == 0) {
      message.nack(new Throwable("Random failure to process a record.")).toCompletableFuture().join();
      return null;
    }

    String value = String.valueOf(message.getPayload());
    value += " consumed in pod (" + InetAddress.getLocalHost().getHostName() + ")";

    message.ack().toCompletableFuture().join();

    return message.withPayload(value);
  }
}
