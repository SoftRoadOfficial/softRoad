package org.softRoad.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Procedure;
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
public class ProcedureControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Procedure procedure = new Procedure();
        procedure.title = "someNewProcedure";
        procedure.description = "someNewProcedureDescription";
        procedure.confirmed = true;

        User user = User.findById(1);
        procedure.user = user;

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(procedure)
                .when()
                .post("/procedures/create")
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
                .post("/procedures/getAll")
                .then()
                .statusCode(200)
                .body("$.size()", is(3));
    }

    @Test
    @TestTransaction
    public void testUpdateEndpoint() {
        User user = User.findById(1);

        Map<String, Object> modifiedData = new HashMap<>();
        modifiedData.put(Procedure.ID, 1);
        modifiedData.put(Procedure.TITLE, "simpleProcedure");
        modifiedData.put(Procedure.DESCRIPTION, "this is a simple Procedure!");

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(modifiedData)
                .when()
                .patch("/procedures")
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
                .pathParam("id", 4)
                .delete("/procedures/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    @Disabled
    public void testGetProcedureForCategoryInCityEndpoint() {
        User user = User.findById(1);
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(searchCriteria)
                .when()
                .pathParam("cityId", 1)
                .pathParam("categoryId", 1)
                .post("/procedures/cities/{cityId}/categories/{categoryId}")
                .then()
                .statusCode(200)
                .body("$.size", is(2));
    }

    @Test
    @TestTransaction
    @Disabled
    public void testGetProcedureForCategoryEndpoint() {
        User user = User.findById(1);
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(searchCriteria)
                .when()
                .pathParam("id", 1)
                .post("/procedures/categories/{id}")
                .then()
                .statusCode(200)
                .body("$.size", is(2));
    }

    @Test
    @TestTransaction
    @Disabled
    public void testGetProcedureForCityEndpoint() {
        User user = User.findById(1);
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(searchCriteria)
                .when()
                .pathParam("id", 1)
                .post("/procedures/cities/{id}")
                .then()
                .statusCode(200)
                .body("$.size", is(0));
    }

    @Test
    @TestTransaction
    public void testGetCategoriesOfProcedureEndpoint() {
        User user = User.findById(1);
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(searchCriteria)
                .when()
                .pathParam("id", 1)
                .get("/procedures/{id}/categories")
                .then()
                .statusCode(200)
                .body("$.size", is(0));
    }

    @Test
    @TestTransaction
    @Disabled
    public void testAddCategoriesToProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of(1))
                .when()
                .pathParam("id", 1)
                .post("/procedures/{id}/categories/add")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testRemoveCategoriesForProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of())
                .when()
                .pathParam("id", 1)
                .post("/procedures/{id}/categories/remove")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetCitiesOfProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .get("/procedures/{id}/cities")
                .then()
                .statusCode(200)
                .body("$.size", is(1));
    }

    @Test
    @TestTransaction
    @Disabled
    public void testAddCitiesToProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of(2, 3))
                .when()
                .pathParam("id", 1)
                .post("/procedures/{id}/cities")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testRemoveCitiesForProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of())
                .when()
                .pathParam("id", 1)
                .post("/procedures/{id}/cities/remove")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetCommentsOfProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .get("/procedures/{id}/comments")
                .then()
                .statusCode(200)
                .body("$.size", is(2));
    }

    @Test
    @TestTransaction
    public void testGetStepsOfProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .get("/procedures/{id}/steps")
                .then()
                .statusCode(200)
                .body("$.size", is(0));
    }

    @Test
    @TestTransaction
    public void testGetTagsOfProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .get("/procedures/{id}/tags")
                .then()
                .statusCode(200)
                .body("$.size", is(0));
    }

    @Test
    @TestTransaction
    public void testAddTagsToProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of(2, 3))
                .when()
                .pathParam("id", 1)
                .post("/procedures/{id}/tags/add")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testRemoveTagsForProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of())
                .when()
                .pathParam("id", 1)
                .post("/procedures/{id}/tags/remove")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetUpdateRequestsOfProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .get("/procedures/{id}/updateRequests")
                .then()
                .statusCode(200)
                .body("$.size", is(0));
    }

}
