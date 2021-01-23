package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.City;
import org.softRoad.models.Procedure;
import org.softRoad.models.ProcedureCity;
import org.softRoad.models.query.HqlQuery;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.softRoad.models.Tables.CITIES;
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
            throw new InvalidDataException("City not found");
        if (procedure == null)
            throw new InvalidDataException("Procedure not found");

        checkState(procedure.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.UPDATE_PROCEDURE));

        entityManager.createNativeQuery(
                String.format("insert into %s(%s, %s) values(:cid,:pid)",
                        PROCEDURE_CITY, ProcedureCity.CITY_ID, ProcedureCity.PROCEDURE_ID))
                .setParameter("cid", cid)
                .setParameter("pid", pid)
                .executeUpdate();

        return Response.ok().build();
    }

    @Transactional
    public List<Procedure> getProceduresForCity(SearchCriteria searchCriteria, Integer id) {
        City city = City.findById(id);
        if (city == null)
            throw new InvalidDataException("Category not found");

        Query q = QueryUtils.nativeQuery(entityManager, Procedure.class)
                .baseQuery(new HqlQuery("select c.* from %s left join %s as c on c.%s=%s",
                        PROCEDURE_CITY, CITIES, City.ID, ProcedureCity.fields(ProcedureCity.CITY_ID)))
                .addFilter(new HqlQuery("%s=:idd", City.ID).setParameter("idd", city.id))
                .searchCriteria(searchCriteria)
                .build();
        return q.getResultList();

    }

    @Transactional
    public Response removeCitiesForProcedure(Integer pid, List<Integer> citiesId) {
        Procedure procedure = Procedure.findById(pid);
        if (procedure == null)
            throw new InvalidDataException("Procedure not found");

        checkState(procedure.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.UPDATE_PROCEDURE));

        for (Integer cityId : citiesId) {
            City city = City.findById(cityId);
            if (city == null)
                throw new InvalidDataException("City not found");

            entityManager.createNativeQuery(
                    String.format("delete from %s where %s=:cid and %s=:pid",
                            PROCEDURE_CITY, ProcedureCity.CITY_ID, ProcedureCity.PROCEDURE_ID))
                    .setParameter("cid", cityId)
                    .setParameter("pid", pid)
                    .executeUpdate();
        }

        return Response.ok().build();
    }
}
