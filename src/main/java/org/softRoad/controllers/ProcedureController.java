package org.softRoad.controllers;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.softRoad.models.Category;
import org.softRoad.models.City;
import org.softRoad.models.Comment;
import org.softRoad.models.Procedure;
import org.softRoad.models.Step;
import org.softRoad.models.Tag;
import org.softRoad.models.UpdateRequest;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.ProcedureService;
import org.softRoad.utils.Diff;

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
    public Response create(@Valid Procedure procedure)
    {
        return procedureService.create(procedure);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff Procedure procedure)
    {
        return procedureService.update(procedure);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/cities/add")
    public Response addCitiesToProcedure(@PathParam("id") Integer id, @NotNull List<Integer> cityIds) {
        return procedureService.addCitiesToProcedure(id, cityIds);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/cities/remove")
    public Response removeCitiesForProcedure(@PathParam("id") Integer id, @NotNull List<Integer> cityIds) {
        return procedureService.removeCitiesForProcedure(id, cityIds);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/categories/add")
    public Response addCategoriesToProcedure(@PathParam("id") Integer id, @NotNull List<Integer> categoryIds) {
        return procedureService.addCategoriesToProcedure(id, categoryIds);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/categories/remove")
    public Response removeCategoriesForProcedure(@PathParam("id") Integer id, @NotNull List<Integer> categoryIds) {
        return procedureService.removeCategoriesForProcedure(id, categoryIds);
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/updateRequests")
    public Set<UpdateRequest> getUpdateRequestsOfProcedure(@PathParam("id") Integer id) 
    {
        return procedureService.getUpdateRequestsOfProcedure(id);
    }    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/steps")
    public Set<Step> getStepsOfProcedure(@PathParam("id") Integer id) 
    {
        return procedureService.getStepsOfProcedure(id);
    }    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/comments")
    public Set<Comment> getCommentsOfProcedure(@PathParam("id") Integer id) 
    {
        return procedureService.getCommentsOfProcedure(id);
    }    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/cities")
    public Set<City> getCitiesOfProcedure(@PathParam("id") Integer id) 
    {
        return procedureService.getCitiesOfProcedure(id);
    }    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/categories")
    public Set<Category> getCategoriesOfProcedure(@PathParam("id") Integer id) 
    {
        return procedureService.getCategoriesOfProcedure(id);
    }    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/tags")
    public Set<Tag> getTagsOfProcedure(@PathParam("id") Integer id) 
    {
        return procedureService.getTagsOfProcedure(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<Procedure> getAll(@NotNull SearchCriteria searchCriteria) {
        return procedureService.getAll(searchCriteria);
    }

}
