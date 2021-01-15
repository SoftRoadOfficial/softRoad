package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Step;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class StepControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        // relation not provided
//        Step step = new Step();
//        step.title = "TheImpStep";
//        step.description = "ThisIsAVeryImportantStep!";
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(step)
//                .when()
//                .post("/steps/create")
//                .then()
//                .statusCode(201);
    }

}
