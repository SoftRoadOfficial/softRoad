package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "audit_logs")
public class AuditLog extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String TIME = "time";
    @Transient
    public final static String PAYLOAD = "payload";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    @Column(name = "time", nullable = false)
    public Instant time;

    public String payload;

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setTime(Instant time) {
        this.time = time;
        presentFields.add("time");
    }

    public void setPayload(String payload) {
        this.payload = payload;
        presentFields.add("payload");
    }

    public static String field(String fieldName) {
        return QueryUtils.field(AuditLog.class, fieldName);
    }
}
