package org.softRoad.services;

import org.softRoad.models.AuditLog;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class AuditLogService extends CrudService<AuditLog> {

    @Inject
    EntityManager entityManager;

    public AuditLogService() {
        super(AuditLog.class);
    }

    @Override
    protected boolean hasPermission(PermissionType type) {
        if (type == PermissionType.CREATE)
            return true;
        return super.hasPermission(type);
    }
}
