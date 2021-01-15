package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Role;
import org.softRoad.models.query.SearchCriteria;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class RoleControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Role role = new Role();
        role.name = "MainAdmin";

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(role)
                .when()
                .post("/roles/create")
                .then()
                .statusCode(201);
    }

    @Test
    @TestTransaction
    public void testGetAllEndpoint() {
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(searchCriteria)
                .when()
                .post("/roles/getAll")
                .then()
                .statusCode(200);
//                .body("$.size()", is(1));
    }
}
