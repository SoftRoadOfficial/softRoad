package org.softRoad.services;

import org.softRoad.models.Procedure;
import org.softRoad.models.Step;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StepService extends CrudService<Step> {
    @Inject
    EntityManager entityManager;

    public StepService() {
        super(Step.class);
    }

    @Transactional
    public void setStepForProcedure(Step step, Integer pid) {
        Procedure procedure = Procedure.findById(pid);

    }

    @Transactional
    public void setStepsForProcedure(List<Step> steps, Integer pid) {

    }

    @Transactional
    public void removeStepFromProcedure(Integer sid, Integer pid) {
        Procedure procedure = Procedure.findById(pid);
        Step step = Step.findById(sid);

    }

    @Transactional
    public List<Step> getStepsOfProcedure(Integer pid) {
        Procedure procedure = Procedure.findById(pid);

        return null;
    }

}
