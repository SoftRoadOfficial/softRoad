package org.softRoad.controllers;

import org.softRoad.models.AuditLog;
import org.softRoad.services.AuditLogService;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

}
