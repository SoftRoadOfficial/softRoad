package org.softRoad.services;

import org.softRoad.models.Category;
import org.softRoad.models.Procedure;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
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

        return null;
    }

    @Transactional
    public Response setCategoryForProcedure(Integer pid, Integer cid) {

        return null;
    }

    @Transactional
    public Response removeCategoryFromProcedure(Integer pid, Integer cid) {

        return null;
    }

    @Transactional
    public List<Procedure> getProceduresOfCategory(Integer cid) {

        return null;
    }

    @Transactional
    public Response createCategory(Category category) {

        return null;
    }

    @Transactional
    public Category getCategory(Integer cid) {

        return null;
    }
}
