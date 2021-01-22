package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String NAME = "name";
    @Transient
    public final static String TYPE = "type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Type type;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    public Set<Fee> fees = new HashSet<>();

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    public Set<Procedure> procedures = new HashSet<>();

    @ManyToMany(mappedBy = "categories")
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

    public void setType(Type type) {
        this.type = type;
        presentFields.add("type");
    }

    public static String fields(String fieldName, String... fieldNames) {
        return QueryUtils.fields(Category.class, fieldName, fieldNames);
    }

    public enum Type {
        VERIFIED
    }
}
