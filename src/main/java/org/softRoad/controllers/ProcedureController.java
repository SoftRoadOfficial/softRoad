package org.softRoad.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.softRoad.models.Procedure;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.ProcedureService;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

@Path("/procedures")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProcedureController 
{
    
    private final ProcedureService procedureService;


    public ProcedureController(ProcedureService procedureService) 
    {
        this.procedureService = procedureService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid Procedure procedure) {
        return procedureService.create(procedure);
    }    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cities/{id}")
    public List<Procedure> getProcedureForCity(@PathParam("id") Integer id, @NotNull SearchCriteria searchCriteria)
    {
        return procedureService.getProceduresForCity(id, searchCriteria);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("categories/{id}")
    public List<Procedure> getProcedureForCategory(@PathParam("id") Integer id, @NotNull SearchCriteria searchCriteria)
    {
        return procedureService.getProceduresForCategory(id, searchCriteria);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cities/{cityId}/categories/{categoryId}")
    public List<Procedure> getProcedureForCategoryInCity(@PathParam("cityId") Integer cityId, @PathParam("categoryId") Integer categoryId, @NotNull SearchCriteria searchCriteria)
    {
        return procedureService.getProceduresForCategoryInCity(cityId, categoryId, searchCriteria);
    }

}
