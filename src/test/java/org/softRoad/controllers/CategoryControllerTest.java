package org.softRoad.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Category;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class CategoryControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Category category = new Category();
        category.name = "official";
        category.type = Category.Type.VERIFIED;

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

    @Test
    @TestTransaction
    public void testGetCategoryEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("cid", 2)
                .get("/categories/{cid}")
                .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("name", equalTo("Medical"));
    }

    @Test
    @TestTransaction
    public void testUpdateEndpoint() {
        User user = User.findById(1);

        Map<String, Object> modifiedData = new HashMap<>();
        modifiedData.put(Category.ID, 2);
        modifiedData.put(Category.NAME, "Simple Category");

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(modifiedData)
                .when()
                .patch("/categories/update")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testDeleteEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 3)
                .delete("/categories/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    @Disabled
    public void testAddCategoryForProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("pid", 2)
                .pathParam("cid", 1)
                .post("/categories/add")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    @Disabled
    public void testRemoveCategoryFromProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("pid", 2)
                .pathParam("cid", 1)
                .delete("/categories/delete")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    @Disabled
    public void testGetProceduresOfCategoryEndpoint() {
        User user = User.findById(1);
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .body(searchCriteria)
                .pathParam("cid", 1)
                .post("/categories/procedures/{cid}")
                .then()
                .statusCode(200);
    }

}
