package org.acme;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.inject.Singleton;
import java.time.Duration;

@Singleton
public class TickGenerator {
  @Outgoing("ticks")
  Multi<Long> generateTicks() {
    return Multi.createFrom().ticks()
        .every(Duration.ofSeconds(2))
        .onOverflow().drop();
  }
}
