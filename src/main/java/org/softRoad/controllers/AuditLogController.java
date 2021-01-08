package org.softRoad.controllers;

import org.softRoad.models.AuditLog;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.services.AuditLogService;
import org.softRoad.utils.Diff;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/auditLog")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuditLogController {
    private final AuditLogService auditLogService;
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response create(@Valid AuditLog auditLog) {
        return auditLogService.create(auditLog);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public AuditLog get(@PathParam("id") Integer id) {
        return auditLogService.get(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAll")
    public List<AuditLog> getAll(@NotNull SearchCriteria searchCriteria) {
        return auditLogService.getAll(searchCriteria);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Diff AuditLog auditLog) {
        return auditLogService.update(auditLog);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        return auditLogService.delete(id);
    }

}
