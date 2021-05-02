package org.acme.data;

import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.redis.client.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Singleton
public class CustomerService {
  private static final String CUSTOMER_HASH_PREFIX = "cust:";

  @Inject
  ReactiveRedisClient reactiveRedisClient;

  public Multi<Customer> allCustomers() {
    return reactiveRedisClient.keys("*")
        .onItem().transformToMulti(response -> Multi.createFrom().iterable(response).map(Response::toString))
        .onItem().transformToUniAndMerge(key ->
            reactiveRedisClient.hgetall(key)
                .map(resp ->
                    constructCustomer(Long.parseLong(key.substring(CUSTOMER_HASH_PREFIX.length())), resp)));
  }

  public Uni<Customer> getCustomer(Long id) {
    return reactiveRedisClient.hgetall(CUSTOMER_HASH_PREFIX + id)
        .map(resp -> resp.size() > 0
            ? constructCustomer(id, resp)
            : null
        );
  }

  public Uni<Customer> createCustomer(Customer customer) {
    return storeCustomer(customer);
  }

  public Uni<Customer> updateCustomer(Customer customer) {
    return getCustomer(customer.id)
        .onItem().transformToUni((cust) -> {
          if (cust == null) {
            return Uni.createFrom().failure(new NotFoundException());
          }
          cust.name = customer.name;
          return storeCustomer(cust);
        });
  }

  public Uni<Void> deleteCustomer(Long id) {
    return reactiveRedisClient.hdel(Arrays.asList(CUSTOMER_HASH_PREFIX + id, "name"))
        .map(resp -> resp.toInteger() == 1 ? true : null)
        .onItem().ifNull().failWith(new NotFoundException())
        .onItem().ifNotNull().transformToUni(r -> Uni.createFrom().nullItem());
  }

  private Uni<Customer> storeCustomer(Customer customer) {
    return reactiveRedisClient.hmset(Arrays.asList(CUSTOMER_HASH_PREFIX + customer.id, "name", customer.name))
        .onItem().transform(resp -> {
          if (resp.toString().equals("OK")) {
            return customer;
          } else {
            throw new NoSuchElementException();
          }
        });
  }

  Customer constructCustomer(long id, Response response) {
    Customer customer = new Customer();
    customer.id = id;
    customer.name = response.get("name").toString();
    return customer;
  }
}
