package org.softRoad.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Procedure;
import org.softRoad.models.Step;
import org.softRoad.models.User;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class StepControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Step step = new Step();
        step.title = "The Important Step";
        step.description = "This Is A Very Important Step!";

        step.procedure = Procedure.findById(1);
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(step)
                .when()
                .post("/steps/create")
                .then()
                .statusCode(201);
    }

    @Test
    @TestTransaction
    public void testGetStepsOfProcedureEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .pathParam("pid", 2)
                .when()
                .post("/steps/get/{pid}")
                .then();
//                .statusCode(200)
//                .body("$.size", is(1));
    }

}
