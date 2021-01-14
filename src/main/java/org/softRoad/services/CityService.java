package org.softRoad.services;

import org.softRoad.exception.NotFoundException;
import org.softRoad.exception.SoftroadException;
import org.softRoad.models.City;
import org.softRoad.models.Procedure;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class CityService extends CrudService<City> {

    @Inject
    EntityManager entityManager;

    public CityService() {
        super(City.class);
    }

    @Transactional
    public City getCity(Integer cid) {
        City city = City.findById(cid);
        if (city == null)
            throw new NotFoundException("City not found");
        return city;
    }

    @Transactional
    public Response addCityForProcedure(Integer cid, Integer pid) {
        if (!accessControlManager.hasPermission(Permission.WRITE_ROLE))
            throw new SoftroadException("User has no access");
        Procedure procedure = Procedure.findById(pid);
        City city = City.findById(cid);
        if (city == null)
            throw new NotFoundException("City not found");
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        procedure.cities.add(city);
        return Response.ok().build();
    }

    @Transactional
    public Response removeCityFromProcedure(Integer cid, Integer pid) {
        City city = City.findById(cid);
        Procedure procedure = Procedure.findById(pid);
        procedure.cities.remove(city);
        if (city == null)
            throw new NotFoundException("City not found");
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        return Response.ok().build();
    }

    @Transactional
    public List<City> getCitiesOfProcedure(Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        return new ArrayList<>(procedure.cities);
    }

}
