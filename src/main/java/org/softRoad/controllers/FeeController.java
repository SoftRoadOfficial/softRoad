package org.softRoad.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.softRoad.models.Fee;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.FeeService;
import org.softRoad.utils.Diff;

@Path("/fees")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class FeeController 
{
    
    private final FeeService feeService;


    public FeeController(FeeService feeService) 
    {
        this.feeService = feeService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response add(Fee fee) 
    {
        return feeService.addFeeForConsultant(fee);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff Fee fee)
    {
        return feeService.update(fee);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        return feeService.delete(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<Fee> getAll(@NotNull SearchCriteria searchCriteria)
    {
        return feeService.getAll(searchCriteria);   
    }

    
    
}
