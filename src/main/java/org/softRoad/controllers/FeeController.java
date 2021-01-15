package org.softRoad.controllers;

import org.softRoad.models.Fee;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.FeeService;
import org.softRoad.utils.Diff;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/fees")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid Fee fee) {
        return feeService.create(fee);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff Fee fee) {
        return feeService.update(fee);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        return feeService.delete(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<Fee> getAll(@NotNull SearchCriteria searchCriteria) {
        return feeService.getAll(searchCriteria);
    }

}
