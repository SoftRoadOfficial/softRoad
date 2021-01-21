package org.softRoad.controllers;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.softRoad.models.Comment;
import org.softRoad.models.Procedure;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.SecurityUtils;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class CommentControllerTest {

    @Test
    @TestTransaction
    public void testCreateEndpoint() {
        Comment comment = new Comment();
        comment.text = "It is very good!";
        comment.rate = 5;

        User user = User.findById(1);
        comment.user = user;

        comment.procedure = Procedure.findById(2);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(comment)
                .when()
                .post("/comments/create")
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
                .post("/comments/getAll")
                .then()
                .statusCode(200)
                .body("$.size()", is(4));
    }

    @Test
    @TestTransaction
    public void testGetEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 2)
                .get("/comments/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("text", equalTo("very good!!!"))
                .body("rate", equalTo(5))
                .body("user", notNullValue());
    }

    @Test
    @TestTransaction
    public void testUpdateEndpoint() {
        User user = User.findById(1);

        Map<String, Object> data = new HashMap<>();
        data.put(Comment.ID, 2);
        data.put(Comment.TEXT, "very good (updated)");

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .body(data)
                .when()
                .patch("/comments")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testDeleteEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 1)
                .delete("/comments/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    public void testGetRepliesForCommentEndpoint() {
        User user = User.findById(1);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", SecurityUtils.getAuthorizationHeader(user))
                .when()
                .pathParam("id", 2)
                .get("/comments/{id}/replies")
                .then()
                .statusCode(200)
                .body("$.size", is(0));
    }

}
