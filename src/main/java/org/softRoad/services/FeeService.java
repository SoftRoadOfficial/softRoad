package org.softRoad.services;

import org.softRoad.exception.DuplicateDataException;
import org.softRoad.models.Fee;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;


@ApplicationScoped
public class FeeService extends CrudService<Fee> {

    public FeeService() {
        super(Fee.class);
    }

    @Override
    public Response create(Fee fee) {
        long count = Fee.count(Fee.CONSULTANT + "=?1 and " + Fee.CATEGORY + "=?2 and " + Fee.MINUTE + "=?3",
                fee.consultant.id, fee.category.id, fee.minute);
        if (count > 0)
            throw new DuplicateDataException("Duplicate fee for this category!");
        return super.create(fee);
    }
}
