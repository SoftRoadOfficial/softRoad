package org.softRoad.models;

import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "user_verifications")
public class UserVerification extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String PHONE_NUMBER = "phone_number";
    @Transient
    public final static String TOKEN = "token";
    @Transient
    public final static String VERIFIED = "verified";
    @Transient
    public final static String TIME = "time";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    public String phoneNumber;

    @NotNull
    @Column(name = "token", nullable = false)
    public String token;

    @NotNull
    @Column(name = "verified", nullable = false)
    public Boolean verified;

    @NotNull
    @Column(name = "time", nullable = false)
    public Instant time;

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        presentFields.add("phoneNumber");
    }

    public void setToken(String token) {
        this.token = token;
        presentFields.add("token");
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
        presentFields.add("verified");
    }

    public void setTime(Instant time) {
        this.time = time;
        presentFields.add("time");
    }

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(UserVerification.class, fieldName, fieldNames);
    }
}
