package org.softRoad.controllers;

import org.softRoad.models.City;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.CityService;

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
    @Path("add")
    public Response addCityForProcedure(@Valid Integer pid, @Valid Integer cid) {
        return cityService.addCityForProcedure(cid, pid);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cid}/{pid}")
    public Response removeCityFromProcedure(@PathParam("pid") Integer pid, @PathParam("cid") Integer cid) {
        return cityService.removeCityFromProcedure(cid, pid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<City> getAll(@NotNull SearchCriteria searchCriteria) {
        return cityService.getAll(searchCriteria);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("procedure/{pid}")
    public List<City> getCitiesOfProcedure(@PathParam("pid") Integer pid) {
        return cityService.getCitiesOfProcedure(pid);
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
}
