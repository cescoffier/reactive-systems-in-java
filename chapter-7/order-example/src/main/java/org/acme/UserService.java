package org.acme;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.orders.Product;
import org.acme.users.UserProfile;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@ApplicationScoped
public class UserService {

    public Uni<Long> createUser(String name) {
        UserProfile user = new UserProfile();
        user.name = name;
        return Panache.withTransaction(() ->
                user.persist()
                    .onItem().transform(u -> ((UserProfile) u).id));
    }

    public Uni<UserProfile> getUserByName(String name) {
        return UserProfile.findByName(name);
    }

    public Multi<UserProfile> getAllUsers() {
        return UserProfile.streamAll();
    }

    public Uni<UserProfile> getRandomUser() {
        Random random = new Random();
        return UserProfile.count()
                .onItem().transform(l -> random.nextInt(Math.toIntExact(l)))
                .onItem().transformToUni(idx -> Product.findAll().page(idx, 1).firstResult());

    }

}
