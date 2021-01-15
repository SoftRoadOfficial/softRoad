package org.softRoad;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Comment;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CommentControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        // relations not provided
//        Comment comment = new Comment();
//        comment.text = "It is very good!";
//        comment.rate = 5;
//
//        User user = new User();
//        user.phoneNumber = "09172009167";
//        user.email = "mhf1377@gmail.com";
//        user.password = "theMostSecurePassword";
//        user.displayName = "Mahdi";
//        user.persist();
//        comment.user = user;
//
//        given()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .body(comment)
//                .when()
//                .post("/comments/create")
//                .then()
//                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetAllEndpoint() {
        SearchCriteria searchCriteria = new SearchCriteria();

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(searchCriteria)
                .when()
                .post("/comments/getAll")
                .then()
                .statusCode(200);
//                .body("$.size()", is(1));
    }
}
