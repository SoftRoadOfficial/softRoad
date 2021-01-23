package org.softRoad.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.softRoad.models.UpdateRequest;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.UpdateRequestService;
import org.softRoad.utils.Diff;


@Path("/updateRequests")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UpdateRequestController {
    private final UpdateRequestService updateRequestService;
    public UpdateRequestController(UpdateRequestService updateRequestService)
    {
        this.updateRequestService = updateRequestService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid UpdateRequest updateRequest)
    {
        return updateRequestService.create(updateRequest);
    }
    
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff UpdateRequest updateRequest) {
        return updateRequestService.update(updateRequest);
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        return updateRequestService.delete(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<UpdateRequest> getAll(@NotNull SearchCriteria searchCriteria) {
        return updateRequestService.getAll(searchCriteria);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public UpdateRequest get(@PathParam("id") Integer id) {
        return updateRequestService.get(id);
    }

    @GET
    @Path("accept/{id}")
    public Response acceptUpdateRequest(Integer id)
    {
        return updateRequestService.acceptUpdateRequest(id);
    }

}
