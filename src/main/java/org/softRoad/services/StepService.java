package org.softRoad.services;

import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Procedure;
import org.softRoad.models.Step;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class StepService extends CrudService<Step> {
    @Inject
    EntityManager entityManager;

    public StepService() {
        super(Step.class);
    }

    @Override
    protected boolean hasPermission(PermissionType type) {
        return true;
    }

    @Override
    @Transactional
    public Response update(Step obj) {
        Step step = Step.findById(obj.id);
        if (step == null)
            throw new InvalidDataException("Invalid step");
        checkState(step.procedure.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.UPDATE_STEP));
        return super.update(obj);
    }

    @Override
    @Transactional
    public Response delete(Integer id) {
        Step step = Step.findById(id);
        if (step == null)
            throw new InvalidDataException("Invalid step");
        checkState(step.procedure.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.DELETE_STEP));
        return super.delete(id);
    }

    @Transactional
    public List<Step> getStepsOfProcedure(Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        if (procedure == null)
            throw new InvalidDataException("Procedure not found");

        return new ArrayList<>(procedure.steps);
    }

}
