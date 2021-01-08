package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Comment;
import org.softRoad.models.User;
import org.softRoad.models.query.HqlQuery;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.models.query.SearchCriteria;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.softRoad.models.Tables.COMMENTS;
import static org.softRoad.models.Tables.USERS;


@ApplicationScoped
public class CommentService extends CrudService<Comment> {
    @Inject
    EntityManager entityManager;

    public CommentService() {
        super(Comment.class);
    }

    public List<Comment> getCommentsForUser(Integer id, @NotNull SearchCriteria searchCriteria) {
        User user = User.findById(id);
        if (user == null)
            throw new InvalidDataException("Invalid user");

        Query q = QueryUtils.nativeQuery(entityManager, Comment.class)
                .baseQuery(new HqlQuery("select %s.* from %s left join %s as u on u.%s=%s",
                        COMMENTS, COMMENTS, USERS, User.ID, Comment.fields(Comment.USER)))
                .addFilter(new HqlQuery("%s=:idd", User.ID).setParameter("idd", user.id))
                .searchCriteria(searchCriteria)
                .build();

        return q.getResultList();
    }

    public List<Comment> getRepliesForComment(Integer id) {
        Comment comment = Comment.findById(id);
        if (comment == null)
            throw new InvalidDataException("Invalid comment");

        return new ArrayList<>(comment.comments);
    }

}
