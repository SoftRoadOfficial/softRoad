package org.softRoad.services;

import org.softRoad.models.City;
import org.softRoad.models.Procedure;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

public class CityService extends CrudService<City> {

    @Inject
    EntityManager entityManager;

    public CityService() {
        super(City.class);
    }

    @Transactional
    public List<Procedure> getProceduresOfCity(Integer cid) {
        City city = City.findById(cid);

        return null;
    }

    @Transactional
    public Response setCityForProcedure(City city, Integer pid) {
        Procedure procedure = Procedure.findById(pid);

        return null;
    }

    @Transactional
    public Response removeCityFromProcedure(Integer cid, Integer pid) {
        City city = City.findById(cid);
        Procedure procedure = Procedure.findById(pid);

        return null;
    }

    @Transactional
    public List<City> getCitiesOfProcedure(Integer pid) {
        Procedure procedure = Procedure.findById(pid);

        return null;
    }

}
