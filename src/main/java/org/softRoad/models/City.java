package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cities")
public class City extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public String name;

    @ManyToMany(mappedBy = "cities")
    @JsonIgnore
    public Set<Procedure> procedures = new HashSet<>();

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setName(String name) {
        this.name = name;
        this.presentFields.add("name");
    }

    public static String field(String fieldName) {
        return QueryUtils.field(City.class, fieldName);
    }
}
