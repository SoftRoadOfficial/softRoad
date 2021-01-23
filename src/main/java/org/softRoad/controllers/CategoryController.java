package org.softRoad.controllers;

import org.softRoad.models.Category;
import org.softRoad.models.ConsultantProfile;
import org.softRoad.models.Fee;
import org.softRoad.models.Procedure;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.CategoryService;
import org.softRoad.utils.Diff;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Category get(@PathParam("id") Integer id) {
        return categoryService.get(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid Category category) {
        return categoryService.create(category);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update")
    public Response update(@Diff Category category) {
        return categoryService.update(category);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        return categoryService.delete(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("fee/{id}")
    public List<Fee> getFeesForCategory(@PathParam("id") Integer id) {
        return categoryService.getFeesForCategory(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultant/{id}")
    public List<ConsultantProfile> getConsultantsForCategory(@PathParam("id") Integer id) {
        return categoryService.getConsultantsForCategory(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("procedures/{id}")
    public Response addCategoriesForProcedure(@NotNull List<Integer> categoriesId, @PathParam("id") Integer pid) {
        return categoryService.addCategoriesForProcedure(categoriesId, pid);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("procedure/{pid}")
    public Response removeCategoriesForProcedure(@NotNull List<Integer> categoriesId, @PathParam("pid") Integer pid) {
        return categoryService.removeCategoriesForProcedure(categoriesId, pid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("procedures/{cid}")
    public List<Procedure> getProceduresForCategory(@PathParam("cid") Integer cid, @NotNull SearchCriteria searchCriteria) {
        return categoryService.getProceduresForCategory(cid, searchCriteria);
    }

}
