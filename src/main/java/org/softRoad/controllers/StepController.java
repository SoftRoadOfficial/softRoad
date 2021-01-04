package org.softRoad.controllers;

import org.softRoad.models.Step;
import org.softRoad.security.AccessControlManager;
import org.softRoad.services.StepService;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/step")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StepController {
    private final StepService stepService;
    AccessControlManager acm;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("set/step")
    public Response setStepForProcedure(@Valid Integer sid, @Valid Integer pid) {
        Integer userId = acm.getCurrentUserId();
        return stepService.setStepForProcedure(userId, sid, pid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("set/steps")
    public Response setStepsForProcedure(@Valid List<Integer> stepIds, @Valid Integer pid) {
        Integer userId = acm.getCurrentUserId();
        return stepService.setStepsForProcedure(userId, stepIds, pid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response setStepsForProcedure(@Valid Step step) {
        Integer userId = acm.getCurrentUserId();
        return stepService.createStep(userId, step);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/{pid}")
    public List<Step> setStepsForProcedure(@PathParam("pid") Integer pid) {
        return stepService.getStepsOfProcedure(pid);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("remove")
    public Response setStepsForProcedure(@Valid Integer sid, @Valid Integer pid) {
        return stepService.removeStepFromProcedure(sid, pid);
    }

}
