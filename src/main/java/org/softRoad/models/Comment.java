package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comments")
public class Comment extends SoftRoadModel {

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
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    public Comment reply;

    @ManyToOne
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    public Consultation consultation;

    @ManyToOne
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    public Procedure procedure;


    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

}