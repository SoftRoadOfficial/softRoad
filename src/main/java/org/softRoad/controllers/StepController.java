package org.softRoad.controllers;

import org.softRoad.models.Step;
import org.softRoad.services.StepService;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/steps")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StepController {
    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid Step step) {
        return stepService.create(step);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update")
    public Response update(@Valid Step step) {
        return stepService.update(step);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete")
    public Response delete(Integer id) {
        return stepService.delete(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{pid}")
    public List<Step> getStepsOfProcedure(@PathParam("pid") Integer pid) {
        return stepService.getStepsOfProcedure(pid);
    }

}
