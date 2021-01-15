package org.softRoad.controllers;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.softRoad.models.ConsultantProfile;
import org.softRoad.models.Procedure;
import org.softRoad.models.Tag;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.TagService;
import org.softRoad.utils.Diff;

@Path("/tags")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagController 
{
    private final TagService tagService;
    
    public TagController(TagService tagService)
    {
        this.tagService = tagService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid Tag tag) {
        return tagService.create(tag);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff Tag tag) {
        return tagService.update(tag);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        return tagService.delete(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<Tag> getAll(@NotNull SearchCriteria searchCriteria) {
        return tagService.getAll(searchCriteria);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("procedures")
    public Set<Procedure> getProceduresForTags(List<Integer> tagIds)
    {
        return tagService.getProceduresForTags(tagIds);
    } 

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultants")
    public Set<ConsultantProfile> getConsultantsForTags(List<Integer> tagIds)
    {
        return tagService.getConsultantsForTags(tagIds);
    } 
    
}
