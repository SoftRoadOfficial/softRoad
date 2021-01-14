package org.softRoad.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.softRoad.models.Fee;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.FeeService;

@Path("/fees")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeeController 
{
    
    private final FeeService feeService;


    public FeeController(FeeService feeService) 
    {
        this.feeService = feeService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response createFeesForConsultant(List<@Valid Fee> fees) 
    {
        return feeService.createFeesForConsultant(fees);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFeesOfConsultant(List<@Valid Fee> fees)
    {
        return feeService.updateFeesOfConsultant(fees);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFeesOfConsultant(List<Integer> feeIds)
    {
        return feeService.deleteFeesOfConsultant(feeIds);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<Fee> getAll(@NotNull SearchCriteria searchCriteria)
    {
        return feeService.getAll(searchCriteria);   
    }
    
}
