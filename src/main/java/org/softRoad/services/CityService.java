package org.softRoad.services;

import org.softRoad.models.City;
import org.softRoad.models.Procedure;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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

    }

    @Transactional
    public void setCityForProcedure(City city, Integer pid) {
        Procedure procedure = Procedure.findById(pid);

    }

    @Transactional
    public void removeCityFromProcedure(Integer cid, Integer pid) {
        City city = City.findById(cid);
        Procedure procedure = Procedure.findById(pid);

    }

    @Transactional
    public List<City> getCitiesOfProcedure(Integer pid) {
        Procedure procedure = Procedure.findById(pid);

    }

}
