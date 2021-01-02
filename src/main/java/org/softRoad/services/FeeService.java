package org.softRoad.services;
import org.softRoad.models.ConsultantProfile;
import org.softRoad.exception.DuplicateDataException;
import org.softRoad.models.Category;
import org.softRoad.models.Fee;
import org.softRoad.models.query.QueryUtils;
import static org.softRoad.models.Tables.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;


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
        Fee consultantFee = Fee.find(Fee.CONSULTANT + "=?1 and " + Fee.Category + "=?2", fee.consultant, fee.category).firstResult();

        if (fee.isPersistent())
        {
            throw new DuplicateDataException("Duplicate fee for this category!");
        }
        
        if (fee != null){
            super.create(fee);
        }
    }

}
