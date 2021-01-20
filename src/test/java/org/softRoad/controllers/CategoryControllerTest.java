package org.softRoad.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Category;
import org.softRoad.models.User;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CategoryControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Category category = new Category();
        category.name = "official";
        category.type = Category.Type.FINANCE;

        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(category)
                .when()
                .post("/categories/create")
                .then()
                .statusCode(201);
    }
}
