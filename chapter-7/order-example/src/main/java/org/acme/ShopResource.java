package org.acme;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.orders.Order;
import org.acme.orders.Product;
import org.acme.users.UserProfile;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.util.List;

@Path("/shop")
public class ShopResource {

    private final UserService users;
    private final ProductService products;
    private final OrderService orders;

    @Inject
    public ShopResource(UserService users, ProductService products, OrderService orders) {
        this.users = users;
        this.products = products;
        this.orders = orders;
    }

    @POST
    @Path("/users/{name}")
    public Uni<Long> createUser(@QueryParam("name") String name) {
        return users.createUser(name)
                .onItem().invoke(l -> System.out.println("New user created: " + name + ", id: " + l))
                .onFailure().invoke(t -> System.out.println("Cannot create the user " + name + ": " + t.getMessage()));
    }

    @GET
    @Path("/user/{name}")
    public Uni<String> getUser(@PathParam("name") String name) {
        Uni<UserProfile> uni = users.getUserByName(name);
        return uni
                .onItem().transform(user -> user.name)
                .onFailure().recoverWithItem("anonymous");
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<String> users() {
        Multi<UserProfile> users = this.users.getAllUsers();
        return users
                .onItem().transform(user -> user.name);
    }

    @GET
    @Path("/products")
    public Multi<ProductModel> products() {
        return products.getAllProducts()
                .onItem().transform(p -> capitalizeAllFirstLetter(p.name))
                .onItem().transform(ProductModel::new);
    }

    private static class ProductModel {
        public final String name;

        private ProductModel(String name) {
            this.name = name;
        }
    }

    @GET
    @Path("/orders/{user}")
    public Multi<Order> getOrdersForUser(@PathParam("user") String username) {
        return users.getUserByName(username)
                .onItem().transformToMulti(user -> orders.getOrderForUser(user));
    }

    @GET
    @Path("/orders")
    public Multi<Order> getOrdersPerUser() {
        return users.getAllUsers()
                .onItem().transformToMultiAndConcatenate(user -> orders.getOrderForUser(user));

    }

    private static String capitalizeAllFirstLetter(String name) {
        char[] array = name.toCharArray();
        array[0] = Character.toUpperCase(array[0]);

        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }

        return new String(array);
    }

    public void init(@Observes StartupEvent ev) {
        Order o1 = new Order();
        Product p1 = (Product) Product.find("name", "Pen").firstResult().await().indefinitely();
        Product p2 = (Product) Product.find("name", "Hat").firstResult().await().indefinitely();
        o1.products = List.of(p1, p2);
        o1.userId = UserProfile.findByName("Bob").await().indefinitely().id;
        Panache.withTransaction(() -> Order.persist(o1)).await().indefinitely();
    }

    @GET
    @Path("/recommendations")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Product> getRecommendations() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onOverflow().drop()
                .onItem().transformToUniAndConcatenate(x -> products.getRecommendedProduct());
    }

    public Uni<String> addUser(String name) {
        return users.createUser(name)
                .onItem().transform(id -> "New User " + name + " inserted")
                .onFailure().recoverWithItem(failure -> "User not inserted: " + failure.getMessage());
    }

    @GET
    @Path("/random-recommendation")
    public Uni<String> getRecommendation() {
        Uni<UserProfile> uni1 = users.getRandomUser();
        Uni<Product> uni2 = products.getRecommendedProduct();
        return Uni.combine().all().unis(uni1, uni2).asTuple()
                .onItem().transform(tuple -> "Hello " + tuple.getItem1().name + ", we recommend you "
                        + tuple.getItem2().name);
    }

    @GET
    @Path("/random-recommendations")
    public Multi<String> getRandomRecommendations() {
        Multi<UserProfile> u = Multi.createFrom().ticks().every(Duration.ofSeconds(1)).onOverflow().drop()
                .onItem().transformToUniAndConcatenate(x -> users.getRandomUser());
        Multi<Product> p = Multi.createFrom().ticks().every(Duration.ofSeconds(1)).onOverflow().drop()
                .onItem().transformToUniAndConcatenate(x -> products.getRecommendedProduct());

        return Multi.createBy().combining().streams(u, p).asTuple()
                .onItem().transform(tuple -> "Hello " + tuple.getItem1().name + ", we recommend you "
                        + tuple.getItem2().name);
    }
}