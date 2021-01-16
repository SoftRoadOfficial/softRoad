package org.softRoad.services;

import org.softRoad.exception.DuplicateDataException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.ConsultantProfile;
import org.softRoad.models.Procedure;
import org.softRoad.models.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class TagService extends CrudService<Tag> {

    public TagService() {
        super(Tag.class);
    }

    @Override
    protected boolean hasPermission(PermissionType type) {
        if (List.of(PermissionType.DELETE, PermissionType.UPDATE).contains(type))
            return super.hasPermission(type);
        return true;
    }

    @Override
    @Transactional
    public Response create(Tag tag) {
        long count = Tag.count(Tag.NAME + "=?1", tag.name);
        if (count > 0)
            throw new DuplicateDataException("Duplicate Tag for this category!");
        return super.create(tag);
    }

    @Transactional
    public Set<Procedure> getProceduresForTags(List<Integer> tagIds) {
        Set<Procedure> procedures = new HashSet<>();
        for (Integer tagId : tagIds) {
            Tag tag = Tag.findById(tagId);
            if (tag == null)
                throw new InvalidDataException("Invalid tag");
            procedures.addAll(tag.procedures);
        }
        return procedures;
    }

    @Transactional
    public Set<ConsultantProfile> getConsultantsForTags(List<Integer> tagIds) {
        Set<ConsultantProfile> consultants = new HashSet<>();
        for (Integer tagId : tagIds) {
            Tag tag = Tag.findById(tagId);
            if (tag == null)
                throw new InvalidDataException("Invalid tag");
            consultants.addAll(tag.consultants);
        }
        return consultants;
    }
}
