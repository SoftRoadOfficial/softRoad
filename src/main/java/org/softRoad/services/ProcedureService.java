package org.softRoad.services;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import org.softRoad.exception.ForbiddenException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.exception.NotFoundException;
import org.softRoad.models.Category;
import org.softRoad.models.City;
import org.softRoad.models.Comment;
import org.softRoad.models.Procedure;
import org.softRoad.models.ProcedureCategories;
import org.softRoad.models.ProcedureCities;
import org.softRoad.models.Step;
import org.softRoad.models.Tag;
import org.softRoad.models.UpdateRequest;
import org.softRoad.models.query.HqlQuery;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.AccessControlManager;

import static org.softRoad.models.Tables.*;


@ApplicationScoped
public class ProcedureService extends CrudService<Procedure>
{
    @Inject
    EntityManager entityManager;

    @Inject
    AccessControlManager accessControlManager;
    

    public ProcedureService() 
    {
        super(Procedure.class);
    }

    @Transactional
    public Response addCitiesToProcedure(Integer procedureId, List<Integer> cityIds) {
        Procedure procedure = Procedure.findById(procedureId);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        for (Integer cityId : cityIds)
        {
            City city = City.findById(cityId);
            if (city == null)
                throw new InvalidDataException("Invalid city");
            entityManager.createNativeQuery(String.format("insert into %s(%s, %s) values(:procedureId,:cityId)",
                    PROCEDURE_CITIES, ProcedureCities.PROCEDURE_ID, ProcedureCities.CITIES_ID))
                    .setParameter("procedureId", procedure.id)
                    .setParameter("cityId", city.id).executeUpdate();        
        }
        return Response.ok().build();
    }   

    @Transactional
    public Response addCategoriesToProcedure(Integer procedureId, List<Integer> categoryIds) {
        Procedure procedure = Procedure.findById(procedureId);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        for (Integer categoryId : categoryIds)
        {
            Category category = Category.findById(categoryId);
            if (category == null)
                throw new InvalidDataException("Invalid category");
            entityManager.createNativeQuery(String.format("insert into %s(%s, %s) values(:procedureId,:categoryId)",
                    PROCEDURE_CATEGORIES, ProcedureCategories.PROCEDURE_ID, ProcedureCategories.CATEGORIES_ID))
                    .setParameter("procedureId", procedure.id)
                    .setParameter("categoryId", category.id).executeUpdate();        
        }
        return Response.ok().build();
    }   

    @Transactional
    public Response removeCitiesForProcedure(Integer procedureId, List<Integer> cityIds) {
        Procedure procedure = Procedure.findById(procedureId);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        for (Integer cityId : cityIds) 
        {
            Category city = Category.findById(cityId);
            if (city == null)
                throw new InvalidDataException("Invalid city");
            entityManager.createNativeQuery(String.format("delete from %s where %s=:procedureId and %s=:cityId",
                    PROCEDURE_CITIES, ProcedureCities.PROCEDURE_ID, ProcedureCities.CITIES_ID))
                    .setParameter("procedureId", procedure.id)
                    .setParameter("cityId", city.id).executeUpdate();
        }    
        return Response.ok().build();
    }   

    @Transactional
    public Response removeCategoriesForProcedure(Integer procedureId, List<Integer> categoryIds) {
        Procedure procedure = Procedure.findById(procedureId);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        for (Integer categoryId : categoryIds) 
        {
            Category category = Category.findById(categoryId);
            if (category == null)
                throw new InvalidDataException("Invalid category");
            entityManager.createNativeQuery(String.format("delete from %s where %s=:procedureId and %s=:categoryId",
                    PROCEDURE_CATEGORIES, ProcedureCategories.PROCEDURE_ID, ProcedureCategories.CATEGORIES_ID))
                    .setParameter("procedureId", procedure.id)
                    .setParameter("categoryId", category.id).executeUpdate();
        }    
        return Response.ok().build();
    }   
    
    @Override
    @Transactional
    public Response update(Procedure procedure)
    {     
        if (accessControlManager.getCurrentUser().id != procedure.user.id)
            throw new ForbiddenException("Forbiden function"); 
        Procedure oldProcedure = Procedure.findById(procedure.id);
        if (oldProcedure == null)
            throw new NotFoundException("Procedure Not Found");
        super.update(procedure);
        return Response.ok().build();
    }

    @Transactional
    public Set<UpdateRequest> getUpdateRequestsOfProcedure(Integer id)
    {
        Procedure procedure = Procedure.findById(id);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        return procedure.updateRequests;
    }
    
    @Transactional
    public Set<Step> getStepsOfProcedure(Integer id)
    {
        Procedure procedure = Procedure.findById(id);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        return procedure.steps;

    }

    @Transactional
    public Set<Comment> getCommentsOfProcedure(Integer id)
    {
        Procedure procedure = Procedure.findById(id);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        return procedure.comments;

    }

    @Transactional
    public Set<City> getCitiesOfProcedure(Integer id)
    {
        Procedure procedure = Procedure.findById(id);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        return procedure.cities;
    }

    @Transactional
    public Set<Category> getCategoriesOfProcedure(Integer id)
    {
        Procedure procedure = Procedure.findById(id);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        return procedure.categories;
    }

    @Transactional
    public Set<Tag> getTagsOfProcedure(Integer id)
    {
        Procedure procedure = Procedure.findById(id);
        if (procedure == null)
            throw new InvalidDataException("Invalid procedure");
        return procedure.tags;
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
