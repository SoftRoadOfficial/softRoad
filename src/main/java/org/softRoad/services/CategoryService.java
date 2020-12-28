package org.softRoad.services;

import org.softRoad.models.Category;
import org.softRoad.models.Procedure;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CategoryService extends CrudService<Category>
{

    @Inject
    EntityManager entityManager;

    public CategoryService()
    {
        super(Category.class);
    }

    @Transactional
    public List<Category> getCategoriesOfProcedure(Procedure procedure)
    {
        return null;
    }

    @Transactional
    public void setCategoryForProcedure(Procedure procedure)
    {

    }

    @Transactional
    public void removeCategoryFromProcedure(Procedure procedure)
    {

    }

    @Transactional
    public List<Procedure> getProceduresOfCategory(Category category)
    {
        return null;
    }

}
