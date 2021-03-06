package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "steps")
public class Step extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String TITLE = "title";
    @Transient
    public final static String DESCRIPTION = "description";
    @Transient
    public final static String PROCEDURE = "procedure_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public String title;

    public String description;

    @ManyToOne
    @JoinColumn(name = "procedure_id")
    @JsonIgnore
    public Procedure procedure;

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setDescription(String description) {
        this.description = description;
        presentFields.add("description");
    }

    public void setTitle(String title) {
        this.title = title;
        presentFields.add("title");
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
        presentFields.add("procedure");
    }

    public static String fields(String fieldName, String... fieldNames) {
        return QueryUtils.fields(Step.class, fieldName, fieldNames);
    }
}
