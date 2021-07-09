package org.acme.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product extends PanacheEntity {

    public String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "products", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    public Set<Order> orders = new HashSet<>();


}
