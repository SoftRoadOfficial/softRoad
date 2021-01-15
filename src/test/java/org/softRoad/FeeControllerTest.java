package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Fee;
import org.softRoad.models.query.SearchCriteria;

import javax.ws.rs.core.MediaType;
import java.math.BigInteger;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class FeeControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        // relations are not provided
//        Fee fee = new Fee();
//        fee.amount = new BigInteger("1000000");
//        fee.minute = 45;
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(fee)
//                .when()
//                .post("/fees/create")
//                .then()
//                .statusCode(201);
    }

    @Test
    @TestTransaction
    public void testGetAllEndpoint() {
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(searchCriteria)
                .when()
                .post("/fees/getAll")
                .then()
                .statusCode(200);
//                .body("$.size()", is(1));
    }
}
