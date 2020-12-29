package org.softRoad.services;

import org.softRoad.exception.DuplicateDataException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Role;
import org.softRoad.models.User;
import org.softRoad.models.UserRole;
import org.softRoad.models.dao.LoginUser;
import org.softRoad.security.AuthenticationResponse;
import org.softRoad.security.SecurityUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.softRoad.models.Tables.*;
import org.softRoad.models.query.SearchCriteria;

@ApplicationScoped
public class UserService extends CrudService<User>
{

    @Inject
    EntityManager entityManager;

    public UserService()
    {
        super(User.class);
    }

    @Override
    public User get(Integer id)
    {
        User user = super.get(id);
        if (user != null)
            user.password = "";
        return user;
    }

    @Override
    public List<User> getAll(SearchCriteria searchCriteria)
    {
        return super.getAll(searchCriteria).stream().map((u) -> {
            u.password = "";
            return u;
        }).collect(Collectors.toList());
    }

    @Override
    public Response create(User obj)
    {
        throw new RuntimeException("User should be created through signUp method");
    }

    @Transactional
    public AuthenticationResponse login(LoginUser loginUser)
    {
        User user = User.find(User.PHONE_NUMBER + "=?1 and " + User.PASSWORD + "=?2", loginUser.getPhoneNumber(),
                loginUser.getPassword()).firstResult();
        if (user == null)
            throw new InvalidDataException("Invalid phoneNumber or password");
        return new AuthenticationResponse(SecurityUtils.createJwtToken(user), user.phoneNumber);
    }

    @Transactional
    public AuthenticationResponse signUp(User user)
    {
        if (User.find(User.PHONE_NUMBER + "=?1", user.phoneNumber).count() > 0)
            throw new DuplicateDataException("Duplicated phoneNumber");
        user.enabled = false; //FIXME users should be enabled after email or phone verification
        User.persist(user);
        return new AuthenticationResponse(SecurityUtils.createJwtToken(user), user.email);
    }

    @Transactional
    public List<Role> getRolesForUser(Integer id)
    {
        User user = User.findById(id);
        if (user == null)
            throw new InvalidDataException("Invalid user");
        return new ArrayList<>(user.roles);
    }

    @Transactional
    public List getRolesNotForUser(Integer id)
    {
        User user = User.findById(id);
        if (user == null)
            throw new InvalidDataException("Invalid user");
        return entityManager
                .createNativeQuery(String.format("select * from %s where %s not in ( select %s from %s where %s=:userId )",
                        ROLES, Role.ID, UserRole.ROLE_ID, USER_ROLES, UserRole.USER_ID),
                        Role.class).setParameter("userId", user.id).getResultList();
    }

    @Transactional
    public Response addRolesToUser(Integer id, List<Integer> roleIds)
    {
        User user = User.findById(id);
        if (user == null)
            throw new InvalidDataException("Invalid user");
        for (Integer rId : roleIds) {
            Role r = Role.findById(rId);
            if (r == null)
                throw new InvalidDataException("Invalid role");
            entityManager.createNativeQuery(String.format("insert into %s(%s, %s) values(:roleId,:userId)",
                    USER_ROLES, UserRole.ROLE_ID, UserRole.USER_ID))
                    .setParameter("roleId", r.id)
                    .setParameter("userId", user.id).executeUpdate();
        }

        return Response.ok().build();
    }

    @Transactional
    public Response removeRolesFromUser(Integer id, List<Integer> roleIds)
    {
        User user = User.findById(id);
        if (user == null)
            throw new InvalidDataException("Invalid user");
        for (Integer rId : roleIds) {
            Role r = Role.findById(rId);
            if (r == null)
                throw new InvalidDataException("Invalid role");
            entityManager.createNativeQuery(String.format("delete from %s where %s=:roleId and %s=:userId",
                    USER_ROLES, UserRole.ROLE_ID, UserRole.USER_ID))
                    .setParameter("roleId", r.id)
                    .setParameter("userId", user.id).executeUpdate();
        }

        return Response.ok().build();
    }
}
