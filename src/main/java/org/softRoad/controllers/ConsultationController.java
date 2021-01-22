package org.softRoad.controllers;

import org.softRoad.models.Comment;
import org.softRoad.models.Consultation;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.ConsultationService;
import org.softRoad.utils.Diff;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/consultation")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid Consultation consultation) {
        return consultationService.create(consultation);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        return consultationService.delete(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Consultation get(@PathParam("id") Integer id) {
        return consultationService.get(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<Consultation> getAll(@NotNull SearchCriteria searchCriteria) {
        return consultationService.getAll(searchCriteria);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff Consultation consultation) {
        return consultationService.update(consultation);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/comment")
    public Set<Comment> getCommentsForConsultation(@PathParam("id") Integer id) {
        return consultationService.getCommentsForConsultation(id);
    }

}
