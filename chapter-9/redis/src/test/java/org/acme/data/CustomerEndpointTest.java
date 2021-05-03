package org.acme.data;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.text.IsEmptyString.emptyString;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerEndpointTest {

  @Test
  @Order(1)
  public void setup() {
    given()
        .when()
        .body("{\"name\" : \"Debbie Hall\"}")
        .contentType("application/json")
        .post("/customer")
        .then()
        .statusCode(201)
        .body(
            containsString("Debbie Hall")
        );
    given()
        .when()
        .body("{\"name\" : \"Gary Parmenter\"}")
        .contentType("application/json")
        .post("/customer")
        .then()
        .statusCode(201)
        .body(
            containsString("Gary Parmenter")
        );
    given()
        .when()
        .body("{\"name\" : \"Mary Shoestring\"}")
        .contentType("application/json")
        .post("/customer")
        .then()
        .statusCode(201)
        .body(
            containsString("Mary Shoestring")
        );
    given()
        .when()
        .body("{\"name\" : \"Virginia Mayweather\"}")
        .contentType("application/json")
        .post("/customer")
        .then()
        .statusCode(201)
        .body(
            containsString("Virginia Mayweather")
        );
  }

  @Test
  @Order(2)
  public void testListCustomers() {
    Response response =
        given()
          .when()
          .get("/customer")
          .then()
            .statusCode(200)
            .extract()
            .response();

    assertThat(response.jsonPath().getList("name"))
        .containsExactlyInAnyOrder("Debbie Hall", "Gary Parmenter", "Mary Shoestring", "Virginia Mayweather");
  }

  @Test
  @Order(3)
  public void testGetCustomer() {
    Customer customer =
        given()
        .when()
        .get("/customer/1")
        .then()
        .statusCode(200)
        .extract()
        .as(Customer.class);

    assertThat(customer).isNotNull();
    assertThat(customer.id).isEqualTo(1);
    assertThat(customer.name).isEqualTo("Debbie Hall");
  }

  @Test
  @Order(4)
  public void testGetInvalidCustomer() {
    given()
        .when()
        .get("/customer/445566")
        .then()
        .statusCode(404);
  }

  @Test
  @Order(5)
  public void testCreateCustomer() {
    given()
        .when()
        .body("{\"name\" : \"Dolly Parton\"}")
        .contentType("application/json")
        .post("/customer")
        .then()
        .statusCode(201)
        .body(
            containsString("Dolly Parton")
        );

    Response response =
        given()
            .when()
            .get("/customer")
            .then()
            .statusCode(200)
            .extract()
            .response();

    assertThat(response.jsonPath().getList("name")).hasSize(5);
  }

  @Test
  @Order(6)
  public void testInvalidCustomerIdForCreate() {
    given()
        .when()
        .body("{\"id\" : 45, \"name\" : \"Anthony Sweeney\"}")
        .contentType("application/json")
        .post("/customer")
        .then()
        .statusCode(422)
        .body(emptyString());
  }

  @Test
  @Order(7)
  public void testInvalidCustomerNameForCreate() {
    given()
        .when()
        .body("{\"name\" : \"\"}")
        .contentType("application/json")
        .post("/customer")
        .then()
        .statusCode(422)
        .body(
            emptyString()
        );
  }

  @Test
  @Order(8)
  public void testUpdateCustomer() {
    given()
        .when()
        .body("{\"id\" : 2, \"name\" : \"Marsha Willis\"}")
        .contentType("application/json")
        .put("/customer/2")
        .then()
        .statusCode(200)
        .body(
            containsString("Marsha Willis")
        );
  }

  @Test
  @Order(9)
  public void testEmptyCustomerForUpdate() {
    given()
        .when()
        .body("{}")
        .contentType("application/json")
        .put("/customer/1")
        .then()
        .statusCode(422)
        .body(
          emptyString()
      );
  }

  @Test
  @Order(10)
  public void testInvalidCustomerNameForUpdate() {
    given()
        .when()
        .body("{\"id\" : 2}")
        .contentType("application/json")
        .put("/customer/2")
        .then()
        .statusCode(422)
        .body(
            emptyString()
        );
  }

  @Test
  @Order(11)
  public void testEntityNotFoundForUpdate() {
    given()
        .when()
        .body("{\"id\" : 32432, \"name\" : \"Mary Tyler Moore\"}")
        .contentType("application/json")
        .put("/customer/32432")
        .then()
        .statusCode(404)
        .body(emptyString());
  }

  @Test
  @Order(12)
  public void testDeleteCustomer() {
    Response response =
        given()
            .when()
            .get("/customer")
            .then()
            .statusCode(200)
            .extract()
            .response();

    assertThat(response.jsonPath().getList("name")).hasSize(5);

    given()
        .when()
        .delete("/customer/2")
        .then()
        .statusCode(204)
        .body(emptyString());

    response =
        given()
            .when()
            .get("/customer")
            .then()
            .statusCode(200)
            .extract()
            .response();

    assertThat(response.jsonPath().getList("name")).hasSize(4);
  }

  @Test
  @Order(13)
  public void testEntityNotFoundForDelete() {
    given()
        .when()
        .delete("/customer/9236")
        .then()
        .statusCode(404)
        .body(emptyString());
  }
}
