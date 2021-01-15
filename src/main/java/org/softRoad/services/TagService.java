package org.softRoad.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.softRoad.exception.DuplicateDataException;
import org.softRoad.exception.InvalidDataException;
import org.softRoad.models.ConsultantProfile;
import org.softRoad.models.Procedure;
import org.softRoad.models.Tag;

@ApplicationScoped
public class TagService extends CrudService<Tag>
{
    @Inject
    EntityManager entityManager;
    
    public TagService()
    {
        super(Tag.class);
    }
    
    @Override
    public Response create(Tag tag) {
        long count = Tag.count(Tag.NAME + "=?1",tag.name);
        if (count > 0)
            throw new DuplicateDataException("Duplicate Tag for this category!");
        return super.create(tag);
    }

    @Transactional
    public Set<Procedure> getProceduresForTags(List<Integer> tagIds)
    {
        Set<Procedure> procedures = new HashSet<>();
        for (Integer tagId : tagIds)
        {
            Tag tag = Tag.findById(tagId);
            if (tag == null)
                throw new InvalidDataException("Invalid tag");
            procedures.addAll(tag.procedures);
        }
        return procedures;
    }

    @Transactional
    public Set<ConsultantProfile> getConsultantsForTags(List<Integer> tagIds)
    {
        Set<ConsultantProfile> consultants = new HashSet<>();
        for (Integer tagId : tagIds)
        {
            Tag tag = Tag.findById(tagId);
            if (tag == null)
                throw new InvalidDataException("Invalid tag");
            consultants.addAll(tag.consultants);
        }
        return consultants;
    }
}
