package org.acme.users;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;

import javax.persistence.Entity;

@Entity
public class UserProfile extends PanacheEntity {

    public String name;

    public static Uni<UserProfile> findByName(String name) {
        return UserProfile.find("name", name).firstResult();
    }

}
