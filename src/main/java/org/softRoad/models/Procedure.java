package org.softRoad.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public final static String CREATE_DATA = "created_date";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public String title;

    @NotNull
    public String description;

    @NotNull
    @Column(name = "created_date")
    public Instant createdDate;

    public Boolean confirmed;

    @OneToMany(mappedBy = "procedure")
    public Set<UpdateRequest> updateRequests = new HashSet<>();

    @OneToMany(mappedBy = "procedure")
    public Set<Step> steps = new HashSet<>();

    @OneToMany(mappedBy = "procedure")
    public Set<Comment> comments = new HashSet<>();

    @ManyToMany
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

    public void setId(Integer id) {
        this.id = id;
        this.presentFields.add("id");
    }

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(Procedure.class, fieldName, fieldNames);
    }
}
