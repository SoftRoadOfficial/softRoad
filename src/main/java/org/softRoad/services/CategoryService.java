package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.*;
import org.softRoad.models.query.HqlQuery;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.softRoad.models.Tables.CATEGORIES;
import static org.softRoad.models.Tables.PROCEDURE_CATEGORY;

@ApplicationScoped
public class CategoryService extends CrudService<Category> {

    @Inject
    EntityManager entityManager;

    public CategoryService() {
        super(Category.class);
    }

    @Override
    protected boolean hasPermission(PermissionType type) {
        if (type == PermissionType.READ)
            return true;
        return super.hasPermission(type);
    }


    @Transactional
    public Response addCategoriesForProcedure(List<Integer> categoriesId, Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        if (procedure == null)
            throw new InvalidDataException("Procedure not found");

        checkState(procedure.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.UPDATE_PROCEDURE));

        for (Integer categoryId : categoriesId) {
            Category category = Category.findById(categoryId);
            if (category == null)
                throw new InvalidDataException("Category not found");

            entityManager.createNativeQuery(
                    String.format("insert into %s(%s, %s) values(:cid,:pid)",
                            PROCEDURE_CATEGORY, ProcedureCategory.CATEGORIES_ID, ProcedureCategory.PROCEDURE_ID))
                    .setParameter("cid", categoryId)
                    .setParameter("pid", pid)
                    .executeUpdate();
        }

        return Response.ok().build();
    }

    @Transactional
    public Response removeCategoriesForProcedure(List<Integer> categoriesId, Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        if (procedure == null)
            throw new InvalidDataException("Procedure not found");

        checkState(procedure.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.UPDATE_PROCEDURE));

        for (Integer categoryId : categoriesId) {
            Category category = Category.findById(categoryId);
            if (category == null)
                throw new InvalidDataException("Category not found");
            entityManager.createNativeQuery(
                    String.format("delete from %s where %s=:cid and %s=:pid",
                            PROCEDURE_CATEGORY, ProcedureCategory.CATEGORIES_ID, ProcedureCategory.PROCEDURE_ID))
                    .setParameter("cid", categoryId)
                    .setParameter("pid", pid)
                    .executeUpdate();
        }

        return Response.ok().build();
    }

    @Transactional
    public List<Procedure> getProceduresForCategory(Integer cid, @NotNull SearchCriteria searchCriteria) {
        Category category = Category.findById(cid);
        if (category == null)
            throw new InvalidDataException("Category not found");

        Query q = QueryUtils.nativeQuery(entityManager, Procedure.class)
                .baseQuery(new HqlQuery("select c.* from %s left join %s as c on c.%s=%s",
                        PROCEDURE_CATEGORY, CATEGORIES, Category.ID, ProcedureCategory.fields(ProcedureCategory.CATEGORIES_ID)))
                .addFilter(new HqlQuery("%s=:idd", Category.ID).setParameter("idd", category.id))
                .searchCriteria(searchCriteria)
                .build();
        return q.getResultList();
    }

    @Transactional
    public List<Fee> getFeesForCategory(Integer id) {
        checkState(acm.hasPermission(Permission.READ_FEE));

        Category category = Category.findById(id);
        if (category == null)
            throw new InvalidDataException("Category not found");

        return new ArrayList<>(category.fees);
    }

    @Transactional
    public List<ConsultantProfile> getConsultantsForCategory(Integer id) {
        checkState(acm.hasPermission(Permission.READ_CONSULTANT_PROFILE));

        Category category = Category.findById(id);
        if (category == null)
            throw new InvalidDataException("Category not found");

        return new ArrayList<>(category.consultants);
    }
}
