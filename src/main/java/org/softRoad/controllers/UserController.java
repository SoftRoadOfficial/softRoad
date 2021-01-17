package org.softRoad.controllers;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.softRoad.models.Role;
import org.softRoad.models.User;
import org.softRoad.models.dao.LoginUser;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.AuthenticationResponse;
import org.softRoad.services.UserService;
import org.softRoad.utils.Diff;

import java.util.List;

@Path("/users")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    public AuthenticationResponse login(@Valid LoginUser loginUser) {
        return userService.login(loginUser);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("signup")
    public AuthenticationResponse signUp(@Valid User user) {
        return userService.signUp(user);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<User> getAll(@NotNull SearchCriteria searchCriteria) {
        return userService.getAll(searchCriteria);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public User get(@PathParam("id") Integer id) {
        return userService.get(id);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff User user) {
        return userService.update(user);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        return userService.delete(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/roles/")
    public List<Role> getRolesForUser(@PathParam("id") Integer id) {
        return userService.getRolesForUser(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/roles/inverse")
    public List<Role> getRolesNotForUser(@PathParam("id") Integer id) {
        return userService.getRolesNotForUser(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/roles/add")
    public Response addRolesToUser(@PathParam("id") Integer id, @NotNull List<Integer> roleIds) {
        return userService.addRolesToUser(id, roleIds);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/roles/remove")
    public Response removeRolesFromUser(@PathParam("id") Integer id, @NotNull List<Integer> roleIds) {
        return userService.removeRolesFromUser(id, roleIds);
    }
}
