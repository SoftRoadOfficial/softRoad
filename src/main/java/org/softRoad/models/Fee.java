package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "fees")
public class Fee extends SoftRoadModel {
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
}
