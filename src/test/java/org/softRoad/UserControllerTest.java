package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;

import javax.ws.rs.core.MediaType;

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

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(searchCriteria)
                .when()
                .post("/users/getAll")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }
}
