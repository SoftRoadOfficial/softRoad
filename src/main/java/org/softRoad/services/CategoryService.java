package org.softRoad.services;

import org.softRoad.exception.NotFoundException;
import org.softRoad.exception.SoftroadException;
import org.softRoad.models.Category;
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
public class CategoryService extends CrudService<Category> {

    @Inject
    EntityManager entityManager;

    public CategoryService() {
        super(Category.class);
    }

    @Transactional
    public List<Category> getCategoriesOfProcedure(Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        return new ArrayList<>(procedure.categories);
    }

    @Transactional
    public Response addCategoryForProcedure(Integer pid, Integer cid) {
        if (!accessControlManager.hasPermission(Permission.WRITE_ROLE))
            throw new SoftroadException("User has no access");
        Procedure procedure = Procedure.findById(pid);
        Category category = Category.findById(cid);
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        if (category == null)
            throw new NotFoundException("Category not found");
        procedure.categories.add(category);
        return Response.ok().build();
    }

    @Transactional
    public Response removeCategoryFromProcedure(Integer pid, Integer cid) {
        if (!accessControlManager.hasPermission(Permission.WRITE_ROLE))
            throw new SoftroadException("User has no access");
        Procedure procedure = Procedure.findById(pid);
        Category category = Category.findById(cid);
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        if (category == null)
            throw new NotFoundException("Category not found");
        procedure.categories.remove(category);
        return Response.ok().build();
    }

    @Transactional
    public List<Procedure> getProceduresOfCategory(Integer cid) {
        if (!accessControlManager.hasPermission(Permission.WRITE_ROLE))
            throw new SoftroadException("User has no access");
        Category category = Category.findById(cid);
        return new ArrayList<>(category.procedures);
    }

}
