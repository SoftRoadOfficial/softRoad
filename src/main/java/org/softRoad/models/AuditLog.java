package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Transient
    public final static String USER = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    @Column(name = "time", nullable = false)
    public Instant time;
    public String payload;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(value = "{displayName, id}")
    public User user;

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

    public void setUser(User user) {
        this.user = user;
        presentFields.add("user");
    }

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(AuditLog.class, fieldName, fieldNames);
    }
}
