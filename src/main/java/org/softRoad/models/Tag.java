package org.softRoad.models;

import io.quarkus.security.jpa.RolesValue;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setName(String name) {
        this.name = name;
        presentFields.add("name");
    }

    public static String field(String fieldName) {
        return QueryUtils.field(Tag.class, fieldName);
    }
}
