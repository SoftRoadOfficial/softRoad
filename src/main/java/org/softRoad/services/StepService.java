package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.exception.NotFoundException;
import org.softRoad.exception.SoftroadException;
import org.softRoad.models.*;
import org.softRoad.security.AccessControlManager;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.softRoad.models.Tables.*;

@ApplicationScoped
public class StepService extends CrudService<Step> {
    @Inject
    EntityManager entityManager;

    @Inject
    AccessControlManager accessControlManager;

    public StepService() {
        super(Step.class);
    }

    @Transactional
    public Response addStepForProcedure(Integer sid, Integer pid) {
        if (!accessControlManager.hasPermission(Permission.WRITE_ROLE))
            throw new SoftroadException("User has no access");
        Procedure procedure = Procedure.findById(pid);
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        Step step = Step.findById(sid);
        if (step == null)
            throw new NotFoundException("Step not found");
        entityManager.createNativeQuery(
                String.format("insert into %s(%s, %s) values(:sid,:pid)",
                        STEPS,
                        Step.ID,
                        Procedure.ID
                )).setParameter("sid", sid)
                .setParameter("pid", pid)
                .executeUpdate();
        return Response.ok().build();
    }

    @Transactional
    public Response addStepsForProcedure(List<Integer> stepIds, Integer pid) {
        for (Integer stepId : stepIds) {
            addStepForProcedure(stepId, pid);
        }
        return null;
    }

    @Transactional
    public Response removeStepFromProcedure(Integer sid, Integer pid) {
        if (!accessControlManager.hasPermission(Permission.WRITE_ROLE))
            throw new SoftroadException("User has no access");
        Procedure procedure = Procedure.findById(pid);
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        Step step = Step.findById(sid);
        if (step == null)
            throw new NotFoundException("Step not found");
        entityManager.createNativeQuery(
                String.format("delete from %s where %s=:sid and %s=:pid",
                        STEPS,
                        Step.ID,
                        Procedure.ID
                )).setParameter("sid", sid)
                .setParameter("pid", pid)
                .executeUpdate();
        return Response.ok().build();
    }

    @Transactional
    public List<Step> getStepsOfProcedure(Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        if (procedure == null)
            throw new NotFoundException("Procedure not found");
        return new ArrayList<>(procedure.steps);
    }
}
