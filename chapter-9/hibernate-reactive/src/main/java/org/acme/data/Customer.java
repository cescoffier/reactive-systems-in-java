package org.acme.data;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.acme.data.order.Order;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends PanacheEntity {
  @Column(nullable = false)
  @NotBlank(message = "Customer name can not be blank")
  @Length(min = 3, message = "Customer names must be at least three characters")
  public String name;

  @Transient
  public List<Order> orders = new ArrayList<>();
}
