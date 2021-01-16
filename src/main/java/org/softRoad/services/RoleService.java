package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Role;
import org.softRoad.models.User;
import org.softRoad.models.UserRole;
import org.softRoad.models.query.HqlQuery;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

import static org.softRoad.models.Tables.USERS;
import static org.softRoad.models.Tables.USER_ROLES;

@ApplicationScoped
public class RoleService extends CrudService<Role> {

    @Inject
    EntityManager entityManager;

    public RoleService() {
        super(Role.class);
    }

    @Transactional
    public Response addPermissionsToRole(Integer id, List<Permission> permissions) {
        checkState(hasPermission(PermissionType.UPDATE));
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        role.permissions.addAll(permissions);
        Role.persist(role);
        return Response.ok().build();
    }

    @Transactional
    public Response removePermissionsFromRole(Integer id, List<Permission> permissions) {
        checkState(hasPermission(PermissionType.UPDATE));
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        role.permissions.removeAll(permissions);
        Role.persist(role);
        return Response.ok().build();
    }

    @Transactional
    public Set<Permission> getPermissionsOfRole(Integer id) {
        checkState(hasPermission(PermissionType.READ));
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        return role.permissions;
    }

    @Transactional
    public Response addUsersToRole(Integer id, List<Integer> userIds) {
        acm.checkPermission(Permission.UPDATE_USER);
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        for (Integer uId : userIds) {
            User u = User.findById(uId);
            if (u == null)
                throw new InvalidDataException("Invalid user");
            entityManager.createNativeQuery(String.format("insert into %s(%s, %s) values(:roleId,:userId)",
                    USER_ROLES, UserRole.ROLE_ID, UserRole.USER_ID))
                    .setParameter("roleId", role.id)
                    .setParameter("userId", u.id).executeUpdate();
        }

        return Response.ok().build();
    }

    @Transactional
    public Response removeUsersFromRole(Integer id, List<Integer> userIds) {
        acm.checkPermission(Permission.UPDATE_USER);
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        for (Integer uId : userIds) {
            User u = User.findById(uId);
            if (u == null)
                throw new InvalidDataException("Invalid user");
            entityManager.createNativeQuery(String.format("delete from %s where %s=:roleId and %s=:userId",
                    USER_ROLES, UserRole.ROLE_ID, UserRole.USER_ID))
                    .setParameter("roleId", role.id)
                    .setParameter("userId", u.id).executeUpdate();
        }
        return Response.ok().build();
    }

    public List<User> getUsersForRole(Integer id, @NotNull SearchCriteria searchCriteria) {
        acm.checkPermission(Permission.READ_USER);
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");

        Query q = QueryUtils.nativeQuery(entityManager, User.class)
                .baseQuery(new HqlQuery("select u.* from %s left join %s as u on u.%s=%s",
                        USER_ROLES, USERS, User.ID, UserRole.fields(UserRole.USER_ID)))
                .addFilter(new HqlQuery("%s=:idd", UserRole.ROLE_ID).setParameter("idd", role.id))
                .searchCriteria(searchCriteria)
                .build();
        return q.getResultList();
    }

    public List<User> getUsersNotForRole(Integer id, @NotNull SearchCriteria searchCriteria) {
        acm.checkPermission(Permission.READ_USER);
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");

        Query q = QueryUtils.nativeQuery(entityManager, User.class)
                .baseQuery(new HqlQuery("select * from %s", USERS))
                .addFilter(new HqlQuery("%s not in ( select %s from %s where %s=:idd )",
                        User.fields(User.ID), UserRole.USER_ID, USER_ROLES, UserRole.ROLE_ID)
                        .setParameter("idd", role.id))
                .searchCriteria(searchCriteria)
                .build();

        return q.getResultList();
    }
}
