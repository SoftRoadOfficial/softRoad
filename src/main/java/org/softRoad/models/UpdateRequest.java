package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "update_requests")
public class UpdateRequest extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String ACCEPTED = "accepted";
    @Transient
    public final static String CREATED_DATA = "created_date";
    @Transient
    public final static String PAYLOAD = "payload";
    @Transient
    public final static String PROCEDURE = "procedure_id";
    @Transient
    public final static String USER = "user_id";
    @Transient
    public final static String TYPE = "type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public Boolean accepted;

    @Column(name = "created_date", nullable = false)
    public Instant createdDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Type type;

    @NotNull
    public String payload;

    @ManyToOne
    @JoinColumn(name = "procedure_id")
    @JsonIgnore
    public Procedure procedure;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"roles", "password", "enabled"})
    public User user;

    @PrePersist
    private void setAccepted() {
        this.accepted = false;
    }

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
        this.presentFields.add("accepted");
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
        this.presentFields.add("createdDate");
    }

    public void setPayload(String payload) {
        this.payload = payload;
        this.presentFields.add("payload");
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
        this.presentFields.add("procedure");
    }

    public void setUser(User user) {
        this.user = user;
        presentFields.add("user");
    }

    public void setType(Type type) {
        this.type = type;
        presentFields.add("type");
    }

    public static String fields(String fieldName, String... fieldNames) {
        return QueryUtils.fields(UpdateRequest.class, fieldName, fieldNames);
    }

    public enum Type {
        ADD_STEP,
        DELETE_STEP,
        EDIT_STEP,

        DELETE_PROCEDURE,
        EDIT_PROCEDURE
    }
}
