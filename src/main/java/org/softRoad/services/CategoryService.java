package org.softRoad.services;

import org.softRoad.exception.NotFoundException;
import org.softRoad.models.Category;
import org.softRoad.models.Procedure;
import org.softRoad.models.ProcedureCategory;
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
    public List<Category> getCategoriesOfProcedure(Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        return new ArrayList<>(procedure.categories);
    }

    @Transactional
    public Response addCategoryForProcedure(Integer pid, Integer cid) {
        Procedure procedure = Procedure.findById(pid);
        Category category = Category.findById(cid);
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        if (category == null)
            throw new NotFoundException("Category not found");

        checkState(procedure.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.UPDATE_PROCEDURE));

        entityManager.createNativeQuery(
                String.format("insert into %s(%s, %s) values(:cid,:pid)",
                        PROCEDURE_CATEGORY, ProcedureCategory.CATEGORIES_ID, ProcedureCategory.PROCEDURE_ID))
                .setParameter("cid", cid)
                .setParameter("pid", pid)
                .executeUpdate();

        return Response.ok().build();
    }

    @Transactional
    public Response removeCategoryFromProcedure(Integer pid, Integer cid) {
        Procedure procedure = Procedure.findById(pid);
        Category category = Category.findById(cid);
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        if (category == null)
            throw new NotFoundException("Category not found");

        checkState(procedure.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.UPDATE_PROCEDURE));

        entityManager.createNativeQuery(
                String.format("delete from %s where %s=:cid and %s=:pid",
                        PROCEDURE_CATEGORY, ProcedureCategory.CATEGORIES_ID, ProcedureCategory.PROCEDURE_ID))
                .setParameter("cid", cid)
                .setParameter("pid", pid)
                .executeUpdate();

        return Response.ok().build();
    }

    @Transactional
    public List<Procedure> getProceduresOfCategory(Integer cid, @NotNull SearchCriteria searchCriteria) {
        Category category = Category.findById(cid);
        if (category == null)
            throw new NotFoundException("Category not found");

        Query q = QueryUtils.nativeQuery(entityManager, Procedure.class)
                .baseQuery(new HqlQuery("select c.* from %s left join %s as c on c.%s=%s",
                        PROCEDURE_CATEGORY, CATEGORIES, Category.ID, ProcedureCategory.fields(ProcedureCategory.CATEGORIES_ID)))
                .addFilter(new HqlQuery("%s=:idd", Category.ID).setParameter("idd", category.id))
                .searchCriteria(searchCriteria)
                .build();
        return q.getResultList();
    }

}
