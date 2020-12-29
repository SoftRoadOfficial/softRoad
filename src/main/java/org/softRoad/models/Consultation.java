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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @OneToOne
    @JoinColumn(unique = true)
    public Fee fee;

    @OneToMany(mappedBy = "consultation")
    public Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "consultations", allowSetters = true)
    public ConsultantProfile consultant;

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

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(Consultation.class, fieldName, fieldNames);
    }
}
