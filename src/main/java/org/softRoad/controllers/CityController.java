package org.softRoad.controllers;

import org.softRoad.models.City;
import org.softRoad.models.Procedure;
import org.softRoad.services.CityService;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/city")
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
        return cityService.getCity(cid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("procedures/{cid}")
    public List<Procedure> getProceduresOfCity(@PathParam("cid") Integer cid) {
        return cityService.getProceduresOfCity(cid);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("set")
    public Response setCityForProcedure(@Valid Integer pid, @Valid Integer cid) {
        return cityService.setCityForProcedure(cid, pid);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("remove")
    public Response removeCityFromProcedure(@Valid Integer pid, @Valid Integer cid) {
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
        return cityService.createCity(city);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("remove")
    public Response removeCity(@Valid Integer cid) {
        return cityService.removeCity(cid);
    }
}
