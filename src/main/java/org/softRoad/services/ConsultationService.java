package org.softRoad.services;

import org.softRoad.exception.BadRequestException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.Comment;
import org.softRoad.models.Consultation;
import org.softRoad.models.query.SearchCriteria;
import org.softRoad.security.Permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class ConsultationService extends CrudService<Consultation> {

    @Inject
    EntityManager entityManager;

    public ConsultationService() {
        super(Consultation.class);
    }

    @Override
    protected boolean hasPermission(PermissionType type) {
        if (type == PermissionType.DELETE || type == PermissionType.UPDATE)
            return super.hasPermission(type);
        return true;
    }

    @Override
    @Transactional
    public Consultation get(Integer id) {
        Consultation consultation = Consultation.findById(id);
        if (consultation == null)
            throw new InvalidDataException("Invalid consultation id");
        checkState(acm.isCurrentUser(consultation.user.id)
                || acm.isCurrentUser(consultation.consultant.user.id)
                || acm.hasPermission(Permission.READ_CONSULTATION));
        return consultation;
    }

    @Override
    public Response create(Consultation consultation) {
        if (consultation.consultant.user.id.equals(consultation.user.id))
            throw new BadRequestException("Consultant CANNOT consult himself");
        return super.create(consultation);
    }

    @Override
    @Transactional
    public List<Consultation> getAll(SearchCriteria searchCriteria) {
        acm.checkPermission(Permission.READ_CONSULTATION);
        return super.getAll(searchCriteria);
    }

    @Transactional
    public Set<Comment> getCommentsForConsultation(Integer id) {
        Consultation consultation = Consultation.findById(id);
        if (consultation == null)
            throw new InvalidDataException("Invalid consultation id");
        checkState(acm.isCurrentUser(consultation.user.id)
                || acm.isCurrentUser(consultation.consultant.user.id)
                || acm.hasPermission(Permission.READ_CONSULTATION));
        return consultation.comments;
    }
}
