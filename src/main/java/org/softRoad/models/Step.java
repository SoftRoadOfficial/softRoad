package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "steps")
public class Step extends SoftRoadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public String title;

    public String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "steps", allowSetters = true)
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
}
