package org.acme;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.inject.Singleton;
import java.net.InetAddress;

@Singleton
public class Processor {
  @Incoming("ticks")
  @Outgoing("processed")
  String process(Long payload) throws Exception {
    String value = String.valueOf(payload);
    value += " consumed in pod (" + InetAddress.getLocalHost().getHostName() + ")";

    return value;
  }
}
