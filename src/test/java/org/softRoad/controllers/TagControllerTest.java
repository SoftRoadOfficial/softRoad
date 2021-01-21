package org.softRoad.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Tag;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TagControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Tag tag = new Tag();
        tag.name = "official";

        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(tag)
                .when()
                .post("/tags/create")
                .then()
                .statusCode(201);
    }

    @Test
    @TestTransaction
    public void testGetAllEndpoint() {
        SearchCriteria searchCriteria = new SearchCriteria();

        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(searchCriteria)
                .when()
                .post("/tags/getAll")
                .then()
                .statusCode(200)
                .body("$.size()", is(3));
    }

    @Test
    @TestTransaction
    public void testUpdateEndpoint() {
        User user = User.findById(1);

        Map<String, Object> data = new HashMap<>();
        data.put(Tag.ID, 2);
        data.put(Tag.NAME, "Finance & Marketing");

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(data)
                .when()
                .patch("/tags")
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
                .pathParam("id", 1)
                .delete("/tags/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetProceduresForTagsEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of(2))
                .when()
                .post("/tags/procedures")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetConsultantsForTagsEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of(2))
                .when()
                .post("/tags/consultants")
                .then()
                .statusCode(200);
    }

}
