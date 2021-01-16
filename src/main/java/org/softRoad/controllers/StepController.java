package org.softRoad.controllers;

import org.softRoad.models.Step;
import org.softRoad.services.StepService;

import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.NotNull;
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
    public Response createStep(Step step) {
        return stepService.create(step);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/{pid}")
    public List<Step> getStepsOfProcedure(@PathParam("pid") Integer pid) {
        return stepService.getStepsOfProcedure(pid);
    }
}
