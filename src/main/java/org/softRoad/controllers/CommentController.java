package org.softRoad.controllers;

import org.softRoad.models.Comment;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.CommentService;
import org.softRoad.utils.Diff;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/comments")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid Comment comment) {
        return commentService.create(comment);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Comment get(@PathParam("id") Integer id) {
        return commentService.get(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<Comment> getAll(@NotNull SearchCriteria searchCriteria) {
        return commentService.getAll(searchCriteria);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff Comment comment) {
        return commentService.update(comment);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        return commentService.delete(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user/{id}")
    public List<Comment> getUserComments(@PathParam("id") Integer id, @NotNull SearchCriteria searchCriteria) {
        return commentService.getUserComments(id, searchCriteria);
    }
}
