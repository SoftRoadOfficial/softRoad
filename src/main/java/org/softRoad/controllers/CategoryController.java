package org.softRoad.controllers;

import org.softRoad.models.Category;
import org.softRoad.models.Procedure;
import org.softRoad.services.CategoryService;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
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
    @Path("{cid}")
    public Category getCategory(@PathParam("cid") Integer cid) {
        return categoryService.getCategory(cid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list/{pid}")
    public List<Category> getCategoriesOfProcedure(@PathParam("pid") Integer pid) {
        return categoryService.getCategoriesOfProcedure(pid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response addCategoryForProcedure(@Valid Integer cid, @Valid Integer pid) {
        return categoryService.addCategoryForProcedure(pid, cid);
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete")
    public Response removeCategoryFromProcedure(@Valid Integer cid, @Valid Integer pid) {
        return categoryService.removeCategoryFromProcedure(pid, cid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("procedures/{cid}")
    public List<Procedure> getProceduresOfCategory(@PathParam("cid") Integer cid) {
        return categoryService.getProceduresOfCategory(cid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response createCategory(@Valid Category category) {
        return categoryService.createCategory(category);
    }


}
