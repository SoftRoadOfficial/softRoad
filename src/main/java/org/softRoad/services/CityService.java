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

import static org.softRoad.models.Tables.PROCEDURE_CITY;


@ApplicationScoped
public class CityService extends CrudService<City> {

    @Inject
    EntityManager entityManager;

    public CityService() {
        super(City.class);
    }

    @Transactional
    public Response addCityForProcedure(Integer cid, Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        City city = City.findById(cid);
        if (city == null)
            throw new NotFoundException("City not found");
        if (procedure == null)
            throw new NotFoundException("Procedure not found");

        entityManager.createNativeQuery(
                String.format("insert into %s(%s, %s) values(:cid,:pid)",
                        PROCEDURE_CITY,
                        City.ID,
                        Procedure.ID
                )).setParameter("cid", cid)
                .setParameter("pid", pid)
                .executeUpdate();

        return Response.ok().build();
    }

    @Transactional
    public Response removeCityFromProcedure(Integer cid, Integer pid) {
        City city = City.findById(cid);
        Procedure procedure = Procedure.findById(pid);
        if (city == null)
            throw new NotFoundException("City not found");
        if (procedure == null)
            throw new NotFoundException("Procedure not found");

        entityManager.createNativeQuery(
                String.format("delete from %s where %s=:cid and %s=:pid",
                        PROCEDURE_CITY,
                        City.ID,
                        Procedure.ID
                )).setParameter("cid", cid)
                .setParameter("pid", pid)
                .executeUpdate();

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
