package org.softRoad.services;

import com.google.common.base.Strings;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Comment;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class CommentService extends CrudService<Comment> {

    public CommentService() {
        super(Comment.class);
    }

    @Override
    protected boolean hasPermission(PermissionType type) {
        return true;
    }

    @Override
    @Transactional
    public Response create(Comment obj) {
        if (obj.rate != null || !Strings.isNullOrEmpty(obj.text))
            return super.create(obj);
        throw new InvalidDataException("rate or text should be provided");
    }

    @Override
    public Response delete(Integer id) {
        Comment comment = Comment.findById(id);
        checkState(comment.user.id.equals(acm.getCurrentUserId()) || hasPermission(PermissionType.DELETE));
        return super.delete(id);
    }

    @Override
    public Response update(Comment obj) {
        Comment comment = Comment.findById(obj.id);
        checkState(comment.user.id.equals(acm.getCurrentUserId()) || hasPermission(PermissionType.UPDATE));
        return super.update(obj);
    }

    @Transactional
    public List<Comment> getRepliesForComment(Integer id) {
        Comment comment = Comment.findById(id);
        if (comment == null)
            throw new InvalidDataException("Invalid comment");
        return new ArrayList<>(comment.comments);
    }

}
