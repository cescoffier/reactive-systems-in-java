package org.acme.data.order;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OrderService {
  @Inject
  PgPool pgClient;

  public Uni<List<Order>> getOrdersForCustomer(Long customer) {
    return pgClient
        .preparedQuery("SELECT id, customerid, description, total FROM orders WHERE customerid = $1")
        .execute(Tuple.of(customer))
        .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
        .onItem().transform(Order::from)
        .collect().asList();
  }
}
