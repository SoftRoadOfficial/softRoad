package org.softRoad.controllers;

import org.softRoad.models.City;
import org.softRoad.models.Procedure;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.CityService;
import org.softRoad.utils.Diff;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cities")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CityController {
    private final CityService cityService;


    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cid}")
    public City getCity(@PathParam("cid") Integer cid) {
        return cityService.get(cid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid City city) {
        return cityService.create(city);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cid}")
    public Response delete(@PathParam("cid") Integer cid) {
        return cityService.delete(cid);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cid}")
    public Response update(@Diff City city) {
        return cityService.update(city);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("procedures/{id}")
    public List<Procedure> getProceduresForCity(@NotNull SearchCriteria searchCriteria, @PathParam("id") Integer id) {
        return cityService.getProceduresForCity(searchCriteria, id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<City> getAll(@NotNull SearchCriteria searchCriteria) {
        return cityService.getAll(searchCriteria);
    }

}
