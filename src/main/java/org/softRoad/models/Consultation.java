package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "consultations")
public class Consultation extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String CONSULTANT = "consultant_id";
    @Transient
    public final static String FEE = "fee_id";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @OneToOne
    @JoinColumn(name = "fee_id", unique = true)
    public Fee fee;

    @OneToMany(mappedBy = "consultation")
    public Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    @JsonIgnoreProperties(value = "consultations")
    public ConsultantProfile consultant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"roles", "password", "enabled", "cities"})
    public ConsultantProfile user;

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setFee(Fee fee) {
        this.fee = fee;
        presentFields.add("fee");
    }

    public void setConsultant(ConsultantProfile consultant) {
        this.consultant = consultant;
        presentFields.add("consultant");
    }

    public void setUser(ConsultantProfile user) {
        this.user = user;
        presentFields.add("user");
    }

    public static String fields(String fieldName, String... fieldNames) {
        return QueryUtils.fields(Consultation.class, fieldName, fieldNames);
    }
}
