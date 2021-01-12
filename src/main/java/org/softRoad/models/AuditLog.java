package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "audit_logs")
public class AuditLog extends SoftRoadModel
{
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String TIME = "time";
    @Transient
    public final static String PAYLOAD = "payload";
    @Transient
    public final static String USER = "user_id";
    @Transient
    public final static String TYPE = "type";
    @Transient
    public final static String ACTION = "action";
    @Transient
    public final static String OBJECT_ID = "object_id";
    @Transient
    public final static String OBJECT_TYPE = "object_type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    @Column(name = "time", nullable = false)
    public Instant time;
    public String payload;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"roles", "password", "enabled"})
    public User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    public Action action;

    @NotNull
    @Column(name = "object_id")
    public Integer objectId;

    @NotNull
    @Column(name = "object_type")
    public String objectType;

    @PrePersist
    private void setTime() {
        this.time = Instant.now();
    }

    public void setId(Integer id)
    {
        this.id = id;
        presentFields.add("id");
    }

    public void setPayload(String payload)
    {
        this.payload = payload;
        presentFields.add("payload");
    }

    public void setUser(User user)
    {
        this.user = user;
        presentFields.add("user");
    }

    public void setAction(Action action)
    {
        this.action = action;
        presentFields.add("type");
    }

    public void setObjectId(Integer objectId)
    {
        this.objectId = objectId;
        presentFields.add("objectId");
    }

    public void setObjectType(String objectType)
    {
        this.objectType = objectType;
        presentFields.add("objectType");
    }

    public static String fields(String fieldName, String... fieldNames)
    {
        return QueryUtils.fields(AuditLog.class, fieldName, fieldNames);
    }

    public static enum Action
    {
        CREATE,
        DELETE,
        UPDATE
    }

}
