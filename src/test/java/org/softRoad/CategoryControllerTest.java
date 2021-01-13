package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Category;

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

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(category)
                .when()
                .post("/category/create")
                .then()
                .statusCode(200);
    }
}
