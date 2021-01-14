package org.softRoad.services;

import org.softRoad.exception.DuplicateDataException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Fee;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.Response;


@ApplicationScoped
public class FeeService extends CrudService<Fee>
{
    
    @Inject
    EntityManager entityManager;

    public FeeService()
    {
        super(Fee.class);
    }

    @Transactional
    public Response createFeesForConsultant(List<@Valid Fee> fees)
    {
        for (Fee fee : fees){
            Fee consultantFee = Fee.find(Fee.CONSULTANT + "=?1 and " + Fee.CATEGORY + "=?2", fee.consultant, fee.category).firstResult();
            if (consultantFee.isPersistent())
            {
                throw new DuplicateDataException("Duplicate fee for this category!");
            }
            super.create(fee);
        }
        return Response.ok().build();
    }

    @Transactional
    public Response updateFeesOfConsultant(List<@Valid Fee> fees)
    {
        for (Fee fee : fees){
            Fee consultantFee = Fee.find(Fee.CONSULTANT + "=?1 and " + Fee.CATEGORY + "=?2", fee.consultant, fee.category).firstResult();
            if (consultantFee.isPersistent())
            {
                throw new DuplicateDataException("Duplicate fee for this category!");
            }
            super.update(fee);
        }
        return Response.ok().build();
    }

    @Transactional
    public Response deleteFeesOfConsultant(List<Integer> feeIds)
    {
        for (Integer feeId : feeIds){
            Fee fee = Fee.findById(feeId);
            if (fee == null)
                throw new InvalidDataException("Invalid Fee");
            super.delete(fee.id);
        }
        return Response.ok().build();
    }

}
