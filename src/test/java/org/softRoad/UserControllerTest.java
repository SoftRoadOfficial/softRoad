package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;

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

//    @Test
//    @TestTransaction
//    public void testLoginEndpoint() {
//        LoginUser loginUser = new LoginUser();
//        loginUser.setPhoneNumber("09170000000");
//        loginUser.setPassword("test_password");
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(loginUser)
//                .when()
//                .post("/users/login")
//                .then()
//                .statusCode(200)
//                .body("phoneNumber", equalTo("09170000000"))
//                .body("jwtToken", CoreMatchers.notNullValue());
//    }
//
//    @Test
//    @TestTransaction
//    public void testGetEndpoint() {
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .when()
//                .get("/users/1")
//                .then()
//                .statusCode(200);
////                .body("phoneNumber", equalTo("09170000000"))
////                .body("jwtToken", CoreMatchers.notNullValue());
//    }
//
//    @Test
//    @TestTransaction
//    public void testUpdateEndpoint() {
//        User user = User.findById(1);
//        user.displayName = "Mahdi";
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(user)
//                .when()
//                .patch("/users/1")
//                .then()
//                .statusCode(200)
//                .body("phoneNumber", equalTo("09170000000"))
//                .body("jwtToken", CoreMatchers.notNullValue());
//    }
//
//    @Test
//    @TestTransaction
//    public void testDeleteEndpoint() {
//        User user = User.findById(1);
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(user)
//                .when()
//                .delete("/users/1")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    @TestTransaction
//    public void testGetRolesForUserEndpoint() {
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .when()
//                .get("/users/1/roles")
//                .then()
//                .statusCode(200);
//                // TODO: check the list
//    }
//
//    @Test
//    @TestTransaction
//    public void testGetRolesNotForUserEndpoint() {
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .when()
//                .get("/users/2/roles/inverse")
//                .then()
//                .statusCode(200);
//                // TODO: check the list
//    }
//
//    @Test
//    @TestTransaction
//    public void testAddRolesToUserEndpoint() {
//        User user = User.findById(2);
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(user) // list of role_ids
//                .when()
//                .post("/users/2/roles/add")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    @TestTransaction
//    public void testRemoveRolesFromUserEndpoint() {
//        User user = User.findById(2);
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(user) // list of role_ids
//                .when()
//                .post("/users/1/roles/remove")
//                .then()
//                .statusCode(200);
//    }


}
