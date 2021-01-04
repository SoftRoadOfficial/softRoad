package org.softRoad.services;
import org.softRoad.exception.DuplicateDataException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Fee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
    public Response addFeeForConsultant(Fee fee)
    {
        Fee consultantFee = Fee.find(Fee.CONSULTANT + "=?1 and " + Fee.CATEGORY + "=?2", fee.consultant, fee.category).firstResult();

        if (consultantFee.isPersistent())
        {
            throw new DuplicateDataException("Duplicate fee for this category!");
        }
        
        if (fee != null)
            return super.create(fee);
        throw new InvalidDataException("Invalid Data!");
    }

}
