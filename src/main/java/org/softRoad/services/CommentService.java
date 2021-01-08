package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Comment;
import org.softRoad.models.User;
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
        User user = User.findById(id);
        if (user == null)
            throw new InvalidDataException("Invalid user");

        // TODO: search in all comments to find the ones with the user == comment.user_id
//        Query q = QueryUtils.nativeQuery(entityManager, User.class)
//                .baseQuery(new HqlQuery("select u.* from %s left join %s as u on u.%s=%s",
//                        USER_COMMENTS, USERS, User.ID, UserComment.fields(UserComment.USER_ID)))
//                .addFilter(new HqlQuery("%s=:idd", UserComment.COMMENTS_ID).setParameter("idd", comment.id))
//                .searchCriteria(searchCriteria)
//                .build();

//        return q.getResultList();
        return Collections.emptyList();
    }

}
