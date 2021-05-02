package org.acme.data;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.data.order.Order;
import org.acme.data.order.OrderService;
import org.jboss.resteasy.reactive.RestPath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("customer")
public class CustomerResource {
  @Inject
  OrderService orderService;

  @GET
  public Multi<Customer> findAll() {
    return Customer.streamAll(Sort.by("name"));
  }

  @GET
  @Path("{id}")
  public Uni<Response> getCustomer(@RestPath Long id) {
    Uni<Customer> customerUni = Customer.<Customer>findById(id)
        .onItem().ifNull().failWith(new WebApplicationException("Failed to find customer", Response.Status.NOT_FOUND));
    Uni<List<Order>> customerOrdersUni = orderService.getOrdersForCustomer(id);
    return Uni.combine()
        .all().unis(customerUni, customerOrdersUni)
        .combinedWith((customer, orders) -> {
          customer.orders = orders;
          return customer;
        })
        .onItem().transform(customer -> Response.ok(customer).build());
  }

  @POST
  public Uni<Response> createCustomer(@Valid Customer customer) {
    if (customer.id != null) {
      throw new WebApplicationException("Invalid customer set on request", 422);
    }

    return Panache
        .withTransaction(customer::persist)
        .replaceWith(Response.ok(customer).status(Response.Status.CREATED).build());
  }

  @PUT
  @Path("{id}")
  public Uni<Response> updateCustomer(@RestPath Long id, @Valid Customer customer) {
    if (customer.id == null) {
      throw new WebApplicationException("Invalid customer set on request", 422);
    }

    return Panache
        .withTransaction(
            () -> Customer.<Customer>findById(id)
                .onItem().ifNotNull().invoke(entity -> entity.name = customer.name)
        )
        .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
        .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND).build());
  }

  @DELETE
  @Path("{id}")
  public Uni<Response> deleteCustomer(@RestPath Long id) {
    return Panache
        .withTransaction(() -> Customer.deleteById(id))
        .map(deleted -> deleted
            ? Response.ok().status(Response.Status.NO_CONTENT).build()
            : Response.ok().status(Response.Status.NOT_FOUND).build());
  }
}
