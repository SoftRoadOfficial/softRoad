package org.softRoad.security;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.softRoad.exception.ForbiddenException;
import org.softRoad.models.Role;
import org.softRoad.models.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.file.AccessDeniedException;

@ApplicationScoped
public class AccessControlManager {

    @Inject
    JsonWebToken jwt;

    private User root;

    public boolean isRoot() {
        return isRoot(getCurrentUserId());
    }

    public boolean isRoot(Integer id) {
        return getRoot().id.equals(id);
    }

    private User getRoot() {
        if (root == null) {
            root = User.find("username=?1", "root").firstResult();
        }
        return root;
    }

    public Integer getCurrentUserId() {
        User user = getCurrentUser();
        return user == null ? null : user.id;
    }

    public User getCurrentUser() {
        return User.find("username=?1", jwt.getName()).firstResult();
    }

    public boolean isCurrentUser(Integer id) {
        Integer currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(id);
    }

    public boolean isCurrentUser(String username) {
        return jwt.getName() != null && jwt.getName().equals(username);
    }

    public boolean hasPermission(Permission permission) {
        User user = getCurrentUser();
        if (user == null)
            return false;
        if (isRoot(user.id))
            return true;
        for (Role role : user.roles) {
            for (Permission p : role.permissions)
                if (p == permission)
                    return true;
        }
        return false;
    }

    public void checkPermission(Permission... permissions) {
        for (Permission permission : permissions)
            checkState(hasPermission(permission));
    }

    public void checkAnyPermission(Permission... permissions) {
        for (Permission permission : permissions) {
            if (hasPermission(permission))
                return;
        }
        checkState(false);
    }

    public void checkState(boolean hasPermission) {
        if (hasPermission)
            return;
        throw new ForbiddenException();
    }
}
