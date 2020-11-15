package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Role;
import org.softRoad.models.User;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class RoleService extends CrudService<Role> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public Response addPermissionsToRole(Integer id, List<Permission> permissions) {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        role.permissions.addAll(permissions);
        Role.persist(role);
        return Response.ok().build();
    }

    @Transactional
    public Response removePermissionsFromRole(Integer id, List<Permission> permissions) {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        role.permissions.removeAll(permissions);
        Role.persist(role);
        return Response.ok().build();
    }

    @Transactional
    public Set<Permission> getPermissionsOfRole(Integer id) {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        return role.permissions;
    }

    @Transactional
    public Response addUsersToRole(Integer id, List<Integer> userIds) {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        for (Integer uId : userIds) {
            User u = User.findById(uId);
            if (u == null)
                throw new InvalidDataException("Invalid user");
//            u.roles.add(role);
//            role.users.add(u);
//            User.persist(u);
            entityManager.createNativeQuery("insert into user_role(role_id, user_id) values(:roleId,:userId)")
                    .setParameter("roleId", role.id)
                    .setParameter("userId", u.id).executeUpdate();
        }

//        Role.persist(role);
        return Response.ok().build();
    }

    @Transactional
    public Response removeUsersFromRole(Integer id, List<Integer> userIds) {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        for (Integer uId : userIds) {
            User u = User.findById(uId);
            if (u == null)
                throw new InvalidDataException("Invalid user");
//            u.roles.remove(role);
//            role.users.remove(u);
//            User.persist(u);
            entityManager.createNativeQuery("delete from user_role where role_id=:roleId and user_id=:userId")
                    .setParameter("roleId", role.id)
                    .setParameter("userId", u.id).executeUpdate();
        }
//        Role.persist(role);
        return Response.ok().build();
    }

    public List<User> getUsersForRole(Integer id) {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        return new ArrayList<>(role.users);
    }

    public List getUsersNotForRole(Integer id) {
        Role role = Role.findById(id);
        if (role == null)
            throw new InvalidDataException("Invalid role");
        return entityManager
                .createNativeQuery("select * from users where users.id not in ( select user_id from user_role where role_id=:roleId )",
                        User.class)
                .setParameter("roleId", role.id).getResultList();
    }
}
