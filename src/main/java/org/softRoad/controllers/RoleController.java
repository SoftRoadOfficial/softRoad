package org.softRoad.controllers;

import org.softRoad.models.Role;
import org.softRoad.models.User;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.Permission;
import org.softRoad.services.RoleService;
import org.softRoad.utils.Diff;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/roles")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid Role role) {
        return roleService.create(role);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Role get(@PathParam("id") Integer id) {
        return roleService.get(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<Role> getAll(@NotNull SearchCriteria searchCriteria) {
        return roleService.getAll(searchCriteria);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff Role role) {
        return roleService.update(role);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        return roleService.delete(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/users/")
    public List<User> getUsersForRole(@PathParam("id") Integer id) {
        return roleService.getUsersForRole(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/users/inverse")
    public List<User> getUsersNotForRole(@PathParam("id") Integer id) {
        return roleService.getUsersNotForRole(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/users/add")
    public Response addUsersToRole(@PathParam("id") Integer id, @NotNull List<Integer> userIds) {
        return roleService.addUsersToRole(id, userIds);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/users/remove")
    public Response removeUsersFromRole(@PathParam("id") Integer id, @NotNull List<Integer> userIds) {
        return roleService.removeUsersFromRole(id, userIds);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/permissions")
    public Set<Permission> getPermissionsOfRole(@PathParam("id") Integer id) {
        return roleService.getPermissionsOfRole(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/permissions/grant")
    public Response addPermissionsToRole(@PathParam("id") Integer id, @NotNull List<Permission> permissions) {
        return roleService.addPermissionsToRole(id, permissions);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/permissions/revoke")
    public Response removePermissionsFromRole(@PathParam("id") Integer id, @NotNull List<Permission> permissions) {
        return roleService.removePermissionsFromRole(id, permissions);
    }
}
