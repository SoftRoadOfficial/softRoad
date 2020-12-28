package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Role;
import org.softRoad.models.User;
import org.softRoad.models.UserRole;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.models.query.HqlQuery;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import javax.persistence.Query;

@ApplicationScoped
public class RoleService extends CrudService<Role>
{

    @Inject
    EntityManager entityManager;

    public RoleService()
    {
        super(Role.class);
    }

    @Transactional
    public Response addPermissionsToRole(Integer id, List<Permission> permissions)
    {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        role.permissions.addAll(permissions);
        Role.persist(role);
        return Response.ok().build();
    }

    @Transactional
    public Response removePermissionsFromRole(Integer id, List<Permission> permissions)
    {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        role.permissions.removeAll(permissions);
        Role.persist(role);
        return Response.ok().build();
    }

    @Transactional
    public Set<Permission> getPermissionsOfRole(Integer id)
    {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        return role.permissions;
    }

    @Transactional
    public Response addUsersToRole(Integer id, List<Integer> userIds)
    {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        for (Integer uId : userIds) {
            User u = User.findById(uId);
            if (u == null)
                throw new InvalidDataException("Invalid user");
            entityManager.createNativeQuery("insert into user_role(" + UserRole.ROLE_ID + ", " + UserRole.USER_ID
                    + ") values(:roleId,:userId)")
                    .setParameter("roleId", role.id)
                    .setParameter("userId", u.id).executeUpdate();
        }

        return Response.ok().build();
    }

    @Transactional
    public Response removeUsersFromRole(Integer id, List<Integer> userIds)
    {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        for (Integer uId : userIds) {
            User u = User.findById(uId);
            if (u == null)
                throw new InvalidDataException("Invalid user");
            entityManager.createNativeQuery("delete from user_role where " + UserRole.ROLE_ID + "=:roleId and "
                    + UserRole.USER_ID + "=:userId")
                    .setParameter("roleId", role.id)
                    .setParameter("userId", u.id).executeUpdate();
        }
        return Response.ok().build();
    }

    public List<User> getUsersForRole(Integer id, @NotNull SearchCriteria searchCriteria)
    {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");

        Query q = QueryUtils.nativeQuery(entityManager, User.class)
                .baseQuery(new HqlQuery("select u.* from user_role left join users as u on u." + User.ID + "="
                        + UserRole.field(UserRole.USER_ID)))
                .addFilter(new HqlQuery("role_id=:idd").setParameter("idd", role.id))
                .searchCriteria(searchCriteria)
                .build();

        return q.getResultList();
    }

    public List<User> getUsersNotForRole(Integer id, @NotNull SearchCriteria searchCriteria)
    {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");

        Query q = QueryUtils.nativeQuery(entityManager, User.class)
                .baseQuery(new HqlQuery("select * from users"))
                .addFilter(new HqlQuery(User.field(User.ID) + " not in ( select " + UserRole.USER_ID
                        + " from user_role where " + UserRole.ROLE_ID + "=:idd )")
                        .setParameter("idd", role.id))
                .searchCriteria(searchCriteria)
                .build();

        return q.getResultList();
    }
}
