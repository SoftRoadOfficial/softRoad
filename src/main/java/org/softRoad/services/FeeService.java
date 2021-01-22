package org.softRoad.services;

import org.softRoad.exception.DuplicateDataException;
import org.softRoad.models.Fee;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;


@ApplicationScoped
public class FeeService extends CrudService<Fee> {

    public FeeService() {
        super(Fee.class);
    }

    @Override
    protected boolean hasPermission(PermissionType type) {
        return true;
    }

    @Override
    @Transactional
    public Response create(Fee fee) {
        checkState(fee.consultant.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.CREATE_FEE));
        long count = Fee.count(Fee.CONSULTANT + "=?1 and " + Fee.CATEGORY + "=?2 and " + Fee.MINUTE + "=?3",
                fee.consultant.id, fee.category.id, fee.minute);
        if (count > 0)
            throw new DuplicateDataException("Duplicate fee for this category-minute-consultant !");
        return super.create(fee);
    }

    @Override
    @Transactional
    public Response update(Fee obj) {
        Fee fee = Fee.findById(obj.id);
        checkState(fee.consultant.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.UPDATE_FEE));
        return super.update(obj);
    }

    @Override
    @Transactional
    public Response delete(Integer id) {
        Fee fee = Fee.findById(id);
        checkState(fee.consultant.user.id.equals(acm.getCurrentUserId()) || acm.hasPermission(Permission.DELETE_FEE));
        return super.delete(id);
    }
}
