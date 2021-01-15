package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.City;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CityControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        City city = new City();
        city.name = "Shiraz";

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(city)
                .when()
                .post("/cities/create")
                .then()
                .statusCode(201);
    }

//    @Test
//    @TestTransaction
//    public void testGetAllEndpoint() {
//        SearchCriteria searchCriteria = new SearchCriteria();
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(searchCriteria)
//                .when()
//                .post("/cities/getAll")
//                .then()
//                .statusCode(200);
//                .body("$.size()", is(1));
//    }
}
