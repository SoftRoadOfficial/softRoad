package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
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
    public final static String REPLY = "reply_id";
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

    @Column(name = "created_date", nullable = false)
    public Instant createdDate;

    @OneToMany(mappedBy = "reply")
    @JsonIgnore
    public Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "reply_id")
    @JsonIgnore
    public Comment reply;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    @JsonIgnoreProperties(value = {"fee", "consultant", "user", "comments"})
    public Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "procedure_id")
    @JsonIgnoreProperties(value = {"title", "description", "createdDate", "confirmed", "categories", "user"})
    public Procedure procedure;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"roles", "password", "enabled", "cities"})
    public User user;

    @PrePersist
    private void setCreatedDate() {
        this.createdDate = Instant.now();
    }

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
        presentFields.add("text");
    }

    public void setRate(Integer rate) {
        this.rate = rate;
        presentFields.add("rate");
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", text=" + text + ", rate=" + rate + ", createdDate=" + createdDate
                + ", comments=" + comments + ", reply=" + reply + ", consultation=" + consultation + ", procedure="
                + procedure + ", user=" + user + '}';
    }

    public static String fields(String fieldName, String... fieldNames) {
        return QueryUtils.fields(Comment.class, fieldName, fieldNames);
    }
}
