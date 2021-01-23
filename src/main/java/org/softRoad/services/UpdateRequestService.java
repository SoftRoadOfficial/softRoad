package org.softRoad.services;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.softRoad.exception.BadRequestException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.UpdateRequest;
import org.softRoad.security.Permission;

@ApplicationScoped
public class UpdateRequestService extends CrudService<UpdateRequest> {

    public UpdateRequestService() {
        super(UpdateRequest.class);
    }

    @Override
    protected boolean hasPermission(PermissionType type) {
        return true;
    }

    @Override
    @Transactional
    public Response create(UpdateRequest updateRequest) {
        checkState(updateRequest.user.id.equals(acm.getCurrentUserId())
                || acm.hasPermission(Permission.CREATE_UPDATE_REQUEST));
        return super.create(updateRequest);
    }

    @Override
    @Transactional
    public Response update(UpdateRequest updateRequeset) {
        if (updateRequeset.presentFields.contains("accept"))
            throw new BadRequestException("Accept condition can not be changed by user itself.");
        UpdateRequest updateRequest = UpdateRequest.findById(updateRequeset.id);
        checkState(updateRequest.user.id.equals(acm.getCurrentUserId())
                || acm.hasPermission(Permission.UPDATE_UPDATE_REQUEST));
        return super.update(updateRequeset);
    }

    @Override
    @Transactional
    public Response delete(Integer id) {
        UpdateRequest updateRequest = UpdateRequest.findById(id);
        checkState(updateRequest.user.id.equals(acm.getCurrentUserId())
                || acm.hasPermission(Permission.DELETE_UPDATE_REQUEST));
        return super.delete(id);
    }

    @Transactional
    public Response acceptUpdateRequest(Integer id) {
        checkState(acm.hasPermission(Permission.ACCEPT_UPDATE_REQUEST));
        UpdateRequest updateRequest = UpdateRequest.findById(id);
        if (updateRequest == null)
            throw new InvalidDataException("Invalid UpdateRequest");
        updateRequest.accepted = true;
        updateRequest.persist();
        return Response.ok().build();
    }
}
