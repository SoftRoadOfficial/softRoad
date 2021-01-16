package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Procedure;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ProcedureControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Procedure procedure = new Procedure();
        procedure.title = "someNewProcedure";
        procedure.description = "someNewProcedureDescription";
        procedure.confirmed = true;

        User user = User.findById(1);
        procedure.user = user;

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(procedure)
                .when()
                .post("/procedures/create")
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
                .post("/procedures/getAll")
                .then()
                .statusCode(200)
                .body("$.size()", is(3));
    }

}
