package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Procedure;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ProcedureControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        // relations not provided
//        Procedure procedure = new Procedure();
//        procedure.title = "someNewProcedure";
//        procedure.description = "someNewProcedureDescription";
//        procedure.confirmed = true;
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(procedure)
//                .when()
//                .post("/procedures/create")
//                .then()
//                .statusCode(201);
    }

}
