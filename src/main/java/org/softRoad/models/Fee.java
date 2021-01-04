package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;

@Entity
@Table(name = "fees")
public class Fee extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String AMOUNT = "amount";
    @Transient
    public final static String MINUTE = "minute";
    @Transient
    public final static String CONSULTANT = "consultant";
    @Transient
    public final static String CATEGORY = "category";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public Integer amount;

    public Integer minute;

    @ManyToOne
    @JsonIgnoreProperties(value = "fees", allowSetters = true)
    public ConsultantProfile consultant;

    @ManyToOne
    @JsonIgnoreProperties(value = "fees", allowSetters = true)
    public Category category;

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
        presentFields.add("amount");
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
        presentFields.add("minute");
    }

    public void setConsultant(ConsultantProfile consultant) {
        this.consultant = consultant;
        presentFields.add("consultant");
    }

    public void setCategory(Category category) {
        this.category = category;
        presentFields.add("category");
    }

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(Fee.class, fieldName, fieldNames);
    }
}
