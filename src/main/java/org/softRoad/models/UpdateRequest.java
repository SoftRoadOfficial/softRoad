package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "update_requests")
public class UpdateRequest extends SoftRoadModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public Boolean accepted;

    @NotNull
    @Column(name = "create_date", nullable = false)
    public Instant createDate;

    @NotNull
    public String payload;

    @ManyToOne
    @JsonIgnoreProperties(value = "updateRequests", allowSetters = true)
    public Procedure procedure;

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
        this.presentFields.add("accepted");
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
        this.presentFields.add("createDate");
    }

    public void setPayload(String payload) {
        this.payload = payload;
        this.presentFields.add("payload");
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
        this.presentFields.add("procedure");
    }
}
