package org.softRoad;

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
        step.title = "TheImpStep";
        step.description = "ThisIsAVeryImportantStep!";

        User user = User.findById(1);

        step.procedure = Procedure.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(step)
                .when()
                .post("/steps/create")
                .then()
                .statusCode(201);
    }

}
