package org.softRoad.services;

import org.softRoad.models.Procedure;
import org.softRoad.models.Step;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class StepService extends CrudService<Step> {
    @Inject
    EntityManager entityManager;

    public StepService() {
        super(Step.class);
    }

    @Transactional
    public Response setStepForProcedure(Integer userId, Integer sid, Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        Step step = Step.findById(sid);

        return null;
    }

    @Transactional
    public Response setStepsForProcedure(Integer userId, List<Integer> stepIds, Integer pid) {
        Procedure procedure = Procedure.findById(pid);

        return null;
    }

    @Transactional
    public Response removeStepFromProcedure(Integer sid, Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        Step step = Step.findById(sid);


        return null;
    }

    @Transactional
    public List<Step> getStepsOfProcedure(Integer pid) {
        Procedure procedure = Procedure.findById(pid);

        return null;
    }

    @Transactional
    public Response createStep(Integer userId, Step step) {

        return null;
    }

}
