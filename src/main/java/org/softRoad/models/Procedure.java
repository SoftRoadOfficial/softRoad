package org.softRoad.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "procedures")
public class Procedure extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String TITLE = "title";
    @Transient
    public final static String DESCRIPTION = "description";
    @Transient
    public final static String CREATED_DATA = "created_date";
    @Transient
    public final static String USER = "user_id";
    @Transient
    public final static String CONFIRMED = "confirmed";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Transient
    public Double rate;

    @NotNull
    public String title;

    @NotNull
    public String description;

    @Column(name = "created_date", nullable = false)
    public Instant createdDate;

    public Boolean confirmed;

    @OneToMany(mappedBy = "procedure")
    @JsonIgnore
    public Set<UpdateRequest> updateRequests = new HashSet<>();

    @OneToMany(mappedBy = "procedure")
    @JsonIgnore
    public Set<Step> steps = new HashSet<>();

    @OneToMany(mappedBy = "procedure")
    @JsonIgnore
    public Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "procedure_cities",
            joinColumns = @JoinColumn(name = "procedure_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cities_id", referencedColumnName = "id"))
    public Set<City> cities = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "procedure_categories",
            joinColumns = @JoinColumn(name = "procedure_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    public Set<Category> categories = new HashSet<>();


    @ElementCollection
    @CollectionTable(name = "procedure_tags", joinColumns = @JoinColumn(name = "procedure_id"))
    @Column(name = "tag")
    @JsonIgnore
    public Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"roles", "password", "enabled"})
    public User user;

    @PrePersist
    private void setCreatedData(){
        this.createdDate = Instant.now();
    }

    public void setId(Integer id) {
        this.id = id;
        this.presentFields.add("id");
    }

    public void setTitle(String title) {
        this.title = title;
        presentFields.add("title");
    }

    public void setDescription(String description) {
        this.description = description;
        presentFields.add("description");
    }

    public void setUser(User user) {
        this.user = user;
        presentFields.add("user");
    }

    public Double getRate() {
        // TODO: 1/7/2021 calculate avg. rate from database
        return rate;
    }

    // TODO: 1/7/2021 check if database can handle rate calculation

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
        presentFields.add("confirmed");
    }

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(Procedure.class, fieldName, fieldNames);
    }
}
