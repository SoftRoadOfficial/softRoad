package org.softRoad.controllers;

import org.softRoad.models.City;
import org.softRoad.services.CityService;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
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
    @Path("{id}")
    public City getCity(@PathParam("id") Integer cid) {
        return cityService.getCity(cid);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response addCityForProcedure(@Valid Integer pid, @Valid Integer cid) {
        return cityService.addCityForProcedure(cid, pid);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("remove/{cid}/{pid}")
    public Response removeCityFromProcedure(@PathParam("pid") Integer pid, @PathParam("cid") Integer cid) {
        return cityService.removeCityFromProcedure(cid, pid);
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
    public Response createCity(@Valid City city) {
        return cityService.create(city);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("remove/{cid}")
    public Response removeCity(@PathParam("cid") Integer cid) {
        return cityService.delete(cid);
    }
}
