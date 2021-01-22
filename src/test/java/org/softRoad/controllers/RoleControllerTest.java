package org.softRoad.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Role;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.Permission;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RoleControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Role role = new Role();
        role.name = "MainAdmin";

        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(role)
                .when()
                .post("/roles/create")
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
                .post("/roles/getAll")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }

    @Test
    @TestTransaction
    public void testGetEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .get("/roles/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("admin"));
    }

    @Test
    @TestTransaction
    public void testUpdateEndpoint() {
        User user = User.findById(1);

        Map<String, Object> modifiedData = new HashMap<>();
        modifiedData.put(Role.ID, 2);
        modifiedData.put(Role.NAME, "simpleUser");

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(modifiedData)
                .when()
                .patch("/roles")
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
                .pathParam("id", 2)
                .delete("/roles/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetUsersForRoleEndpoint() {
        User user = User.findById(1);
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(searchCriteria)
                .when()
                .pathParam("id", 1)
                .post("/roles/{id}/users")
                .then()
                .statusCode(200)
                .body("$.size", is(2));
    }

    @Test
    @TestTransaction
    public void testGetUsersNotForRoleEndpoint() {
        User user = User.findById(1);
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(searchCriteria)
                .when()
                .pathParam("id", 1)
                .post("/roles/{id}/users/inverse")
                .then()
                .statusCode(200)
                .body("$.size", is(4));
    }

    @Test
    @TestTransaction
    public void testAddUsersToRoleEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of(2))
                .when()
                .pathParam("id", 1)
                .post("/roles/{id}/users/add")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testRemoveUsersFromRoleEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of(3))
                .when()
                .pathParam("id", 1)
                .post("/roles/{id}/users/remove")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetPermissionsOfRoleEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .get("/roles/{id}/permissions")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testAddPermissionsToRoleEndpoint() {
        User user = User.findById(1);
        List<Permission> permissions = List.of(Permission.DELETE_COMMENT, Permission.CREATE_COMMENT);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(permissions)
                .when()
                .pathParam("id", 1)
                .post("/roles/{id}/permissions/grant")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testRemovePermissionsFromRoleEndpoint() {
        User user = User.findById(1);
        List<Permission> permissions = List.of(Permission.UPDATE_FEE, Permission.READ_FEE);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(permissions)
                .when()
                .pathParam("id", 1)
                .post("/roles/{id}/permissions/revoke")
                .then()
                .statusCode(200);
    }
}
