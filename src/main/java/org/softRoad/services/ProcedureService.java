package org.softRoad.services;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Category;
import org.softRoad.models.City;
import org.softRoad.models.Comment;
import org.softRoad.models.Procedure;
import org.softRoad.models.ProcedureCategories;
import org.softRoad.models.ProcedureCities;
import org.softRoad.models.query.HqlQuery;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.models.query.SearchCriteria;
import static org.softRoad.models.Tables.*;


@ApplicationScoped
public class ProcedureService extends CrudService<Procedure>
{
    @Inject
    EntityManager entityManager;
    

    public ProcedureService() 
    {
        super(Procedure.class);
    }

    @Transactional
    public List<Procedure> getProceduresForCity(Integer id, @NotNull SearchCriteria searchCriteria)
    {

        City city = City.findById(id);
        if (city == null)
            throw new InvalidDataException("Invalid city");
        
        Query q = QueryUtils.nativeQuery(entityManager, Procedure.class)
        .baseQuery(new HqlQuery("select u.* from %s as u right join %s on u.%s=%s",
                        PROCEDURES, PROCEDURE_CITIES, Procedure.ID, ProcedureCities.fields(ProcedureCities.PROCEDURE_ID)))
                        .addFilter(new HqlQuery("%s=:idd", ProcedureCities.CITIES_ID).setParameter("idd", city.id))
                .searchCriteria(searchCriteria)
                .build();
        return q.getResultList();        
    }    
    
    @Transactional
    public List<Procedure> getProceduresForCategory(Integer id, @NotNull SearchCriteria searchCriteria)
    {
        
        Category category = Category.findById(id);
        if (category == null)
            throw new InvalidDataException("Invalid categroy");
        
        Query q = QueryUtils.nativeQuery(entityManager, Procedure.class)
        .baseQuery(new HqlQuery("select u.* from %s as u right join %s on u.%s=%s",
        PROCEDURES, PROCEDURE_CATEGORIES, Procedure.ID, ProcedureCategories.fields(ProcedureCategories.PROCEDURE_ID)))
        .addFilter(new HqlQuery("%s=:idd", ProcedureCategories.CATEGORIES_ID).setParameter("idd", category.id))
        .searchCriteria(searchCriteria)
        .build();
        return q.getResultList();        
    }    
    
    @Transactional
    public List<Procedure> getProceduresForCategoryInCity(Integer cityId, Integer categoryId, @NotNull SearchCriteria searchCriteria)
    {
        
        City city = City.findById(cityId);
        if (city == null)
            throw new InvalidDataException("Invalid city");
        Category category = Category.findById(categoryId);
        if (category == null)
            throw new InvalidDataException("Invalid categroy");
        
        Query q = QueryUtils.nativeQuery(entityManager, Procedure.class)
        .baseQuery(new HqlQuery("select u.* from %s as p right join %s as pc on p.%s=pc.%s right join %s as pcat on p.%s=pcat.%s",
                        PROCEDURES, PROCEDURE_CITIES, Procedure.ID, ProcedureCities.PROCEDURE_ID, Procedure.ID, ProcedureCategories.PROCEDURE_ID))
                        .addFilter(new HqlQuery("%s=:idd", ProcedureCities.CITIES_ID).setParameter("idd", city.id))
                        .addFilter(new HqlQuery("%s=:idd", ProcedureCategories.CATEGORIES_ID).setParameter("idd", category.id))
                .searchCriteria(searchCriteria)
                .build();
        return q.getResultList();        
    }

}
