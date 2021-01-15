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

        User user1 = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                // TODO: Really bro?!
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user1))
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
}
