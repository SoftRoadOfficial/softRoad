package org.softRoad;

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
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

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
                .statusCode(200);
        // TODO: check the result
    }

    @Test
    @TestTransaction
    public void testUpdateEndpoint() {
        User user = User.findById(1); //FIXME jsonMapper validates based on User -> complete user is not needed here
        user.displayName = "Mahdi";

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(user)
                .when()
                .patch("/users")
                .then()
                .statusCode(200);
    }

//    @Test
//    @TestTransaction
//    public void testDeleteEndpoint()
//    {
//        User user = User.findById(1);
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
//                .when()
//                .pathParam("id", 1)
//                .delete("/users/{id}")
//                .then()
//                .statusCode(200);
//    }

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
                .statusCode(200);
        // TODO: check the list
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
                .statusCode(200);
        // TODO: check the list
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
