package org.softRoad.services;

import com.google.common.base.Strings;
import org.jose4j.jwk.Use;
import org.softRoad.exception.BadRequestException;
import org.softRoad.exception.ForbiddenException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Comment;
import org.softRoad.models.User;
import org.softRoad.models.UserRole;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
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
    public Response create(Comment comment) {
        if (comment.user == null) {
            User user = acm.getCurrentUser();
            if (user != null)
                comment.user = user;
            else
                throw new ForbiddenException("Comment.user must not be null");
        }
        if ((comment.consultation == null || comment.consultation.id == null)
                == (comment.procedure == null || comment.procedure.id == null))
            throw new BadRequestException("Invalid data");

        if (comment.rate != null || !Strings.isNullOrEmpty(comment.text))
            return super.create(comment);
        throw new InvalidDataException("rate or text should be provided");
    }

    @Override
    @Transactional
    public Response delete(Integer id) {
        Comment comment = Comment.findById(id);
        checkState(comment.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.DELETE_COMMENT));
        return super.delete(id);
    }

    @Override
    @Transactional
    public Response update(Comment obj) {
        if (obj.presentFields.contains("user"))
            throw new BadRequestException("Comment.user can not get changed");
        Comment comment = Comment.findById(obj.id);
        checkState(comment.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.UPDATE_COMMENT));
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
