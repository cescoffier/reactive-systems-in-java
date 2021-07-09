package org.acme;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.orders.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class ProductService {

    private final OrderService orders;

    @Inject
    public ProductService(OrderService orders) {
        this.orders = orders;
    }


    public Uni<Void> createProduct(String name) {
        Product product = new Product();
        product.name = name;

        return Panache.withTransaction(product::persist)
                .replaceWithVoid();
    }

    public Multi<Product> getAllProducts() {
        return Product.streamAll();
    }

    public Multi<Product> getAllOrderedProducts() {
        return orders.getAllOrders()
                .onItem().transformToIterable(order -> order.products)
                .select().distinct();
    }

    public Uni<List<Product>> getAllOrderedProductsAsList() {
        return getAllOrderedProducts()
                .collect().asList();
    }

    public Uni<Product> getRecommendedProduct() {
        Random random = new Random();
        return Product.count()
                .onItem().transform(l -> random.nextInt(Math.toIntExact(l)))
                .onItem().transformToUni(idx -> Product.findAll().page(idx, 1).firstResult());

    }

    public Uni<Product> getProductByName(String name) {
        return Product.find("name", name).firstResult();
    }
}
