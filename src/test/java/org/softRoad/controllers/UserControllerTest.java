package org.softRoad.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.softRoad.models.User;
import org.softRoad.models.dao.LoginUser;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UserControllerTest {

    @Test
    @TestTransaction
    public void testSignUpEndpoint() {
        User user = new User();
        user.phoneNumber = "09172009167";
        user.email = "mhf1377@gmail.com";
        user.password = "theMostSecurePassword";
        user.displayName = "Mahdi";

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(user)
                .when()
                .post("/users/signup")
                .then()
                .statusCode(200)
                .body("phoneNumber", equalTo("09172009167"))
                .body("jwtToken", CoreMatchers.notNullValue());
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
                .post("/users/getAll")
                .then()
                .statusCode(200)
                .body("$.size()", is(4));
    }

    @Test
    @TestTransaction
    public void testLoginEndpoint() {
        LoginUser loginUser = new LoginUser("09170000000", "test_password");

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(loginUser)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .body("phoneNumber", equalTo("09170000000"))
                .body("jwtToken", CoreMatchers.notNullValue());
    }

    @Test
    @TestTransaction
    public void testGetEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .pathParam("id", 1)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("id", CoreMatchers.equalTo(1))
                .body("phoneNumber", CoreMatchers.equalTo("09170000000"))
                .body("email", CoreMatchers.equalTo("test1@test.com"))
                .body("displayName", CoreMatchers.equalTo("test_user1"))
                .body("password", CoreMatchers.equalTo(""));
    }

    @Test
    @TestTransaction
    public void testUpdateEndpoint() {
        User user = User.findById(1);

        Map<String, Object> data = new HashMap<>();
        data.put(User.ID, 1);
        data.put(User.EMAIL, "Mahdi@yahoo.com");

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(data)
                .when()
                .patch("/users")
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
                .pathParam("id", 5)
                .delete("/users/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetRolesForUserEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .get("/users/{id}/roles")
                .then()
                .statusCode(200)
                .body("name", hasItem("admin"));
    }

    @Test
    @TestTransaction
    public void testGetRolesNotForUserEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .get("/users/{id}/roles/inverse")
                .then()
                .statusCode(200)
                .body("name", hasItem("normalUser"));
    }

    @Test
    @TestTransaction
    public void testAddRolesToUserEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of(2))
                .pathParam("id", 1)
                .when()
                .post("/users/{id}/roles/add")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testRemoveRolesFromUserEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(List.of(2))
                .pathParam("id", 1)
                .when()
                .post("/users/{id}/roles/remove")
                .then()
                .statusCode(200);
    }
}
