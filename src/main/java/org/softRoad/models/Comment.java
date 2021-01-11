package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comments")
public class Comment extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String TEXT = "text";
    @Transient
    public final static String RATE = "rate";
    @Transient
    public final static String CREATED_DATE = "created_date";
    @Transient
    public final static String REPLY = "comment_id";
    @Transient
    public final static String CONSULTATION = "consultation_id";
    @Transient
    public final static String PROCEDURE = "procedure_id";
    @Transient
    public final static String USER = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String text;
    public Integer rate;

    @NotNull
    @Column(name = "created_date", nullable = false)
    public Instant createdDate;

    @OneToMany(mappedBy = "reply")
    public Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    public Comment reply;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    public Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "procedure_id")
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    public Procedure procedure;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"roles", "password", "enabled"})
    public User user;


    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setReply(Comment reply) {
        this.reply = reply;
        presentFields.add("reply");
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
        presentFields.add("consultation");
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
        presentFields.add("procedure");
    }

    public void setUser(User user) {
        this.user = user;
        presentFields.add("user");
    }

    public void setText(String text) {
        this.text = text;
        presentFields.add("test");
    }

    public void setRate(Integer rate) {
        this.rate = rate;
        presentFields.add("rate");
    }

    // TODO: 1/7/2021 assign createDate automatically in DB

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(Comment.class, fieldName, fieldNames);
    }
}
