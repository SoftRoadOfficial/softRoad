package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    public Set<Procedure> procedures = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    public Set<ConsultantProfile> consultants = new HashSet<>();

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setName(String name) {
        this.name = name;
        presentFields.add("name");
    }

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(Tag.class, fieldName, fieldNames);
    }
}
