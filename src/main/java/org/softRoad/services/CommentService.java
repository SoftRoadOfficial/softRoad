package org.softRoad.services;

import org.softRoad.models.Comment;
import org.softRoad.models.query.SearchCriteria;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;


@ApplicationScoped
public class CommentService extends CrudService<Comment> {

    @Inject
    EntityManager entityManager;

    public CommentService() {
        super(Comment.class);
    }


    public List<Comment> getUserComments(Integer id, @NotNull SearchCriteria searchCriteria) {
        return Collections.emptyList();
        // TODO: implement this later!
    }

}
