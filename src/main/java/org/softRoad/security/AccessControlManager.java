package org.softRoad.security;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.softRoad.exception.ForbiddenException;
import org.softRoad.models.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccessControlManager
{

    @Inject
    JsonWebToken jwt;

    public Integer getCurrentUserId()
    {
        User user = getCurrentUser();
        return user == null ? null : user.id;
    }

    public User getCurrentUser()
    {
        if (jwt.getName() == null)
            return null;
        return User.find(User.PHONE_NUMBER + "=?1", jwt.getName()).firstResult();
    }

    public boolean isCurrentUser(Integer id)
    {
        Integer currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(id);
    }

    public boolean isCurrentUser(String username)
    {
        return jwt.getName() != null && jwt.getName().equals(username);
    }

    public boolean hasPermission(Permission permission)
    {
        User user = getCurrentUser();
        if (user == null)
            return false;
        return user.roles.stream().anyMatch(role -> (role.permissions.contains(permission)));
    }

    public void checkPermission(Permission... permissions)
    {
        for (Permission permission : permissions)
            checkState(hasPermission(permission));
    }

    public void checkAnyPermission(Permission... permissions)
    {
        for (Permission permission : permissions)
            if (hasPermission(permission))
                return;
        checkState(false);
    }

    private void checkState(boolean hasPermission)
    {
        if (!hasPermission)
            throw new ForbiddenException();
    }
}
