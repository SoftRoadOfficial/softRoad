package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.softRoad.models.query.QueryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "consultant_profiles")
public class ConsultantProfile extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String BIO = "bio";
    @Transient
    public final static String DESCRIPTION = "description";
    @Transient
    public final static String USER = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public String nationalCode; // TODO: 1/7/2021 nationalCode validation

    @Transient
    public Double rate;

    public String bio;

    public String description;

    @OneToMany(mappedBy = "consultant")
    @JsonIgnore
    public Set<Consultation> consultations = new HashSet<>();

    @OneToMany(mappedBy = "consultant")
    public Set<Fee> fees = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "consultant_profile_categories",
            joinColumns = @JoinColumn(name = "consultant_profile_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    public Set<Category> categories = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "consultant_profiles_tags", joinColumns = @JoinColumn(name = "consultant_profiles_id"))
    @Column(name = "tag")
    @JsonIgnore
    public Set<Tag> tags = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @JsonIgnoreProperties(value = {"roles", "password", "enabled"})
    public User user;

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setBio(String bio) {
        this.bio = bio;
        presentFields.add("bio");
    }

    public void setDescription(String description) {
        this.description = description;
        presentFields.add("description");
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
        presentFields.add("nationalCode");
    }

    public void setUser(User user) {
        this.user = user;
        presentFields.add("user");
    }

    public Double getRate() {
        // TODO: 1/7/2021 calculate avg. rate from database
        return rate;
    }

    public static String fields(String fieldName, String ... fieldNames) {
        return QueryUtils.fields(ConsultantProfile.class, fieldName, fieldNames);
    }
}
