package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Category;
import org.softRoad.models.ConsultantProfile;
import org.softRoad.models.Fee;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;
import java.math.BigInteger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class FeeControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Fee fee = new Fee();
        fee.amount = new BigInteger("1000000");
        fee.minute = 45;

        fee.consultant = ConsultantProfile.findById(1);
        fee.category = Category.findById(1);

        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(fee)
                .when()
                .post("/fees/create")
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
                .post("/fees/getAll")
                .then()
                .statusCode(200)
                .body("$.size()", is(3));
    }
}
