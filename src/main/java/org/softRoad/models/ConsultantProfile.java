package org.softRoad.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "consultant_profiles")
public class ConsultantProfile extends SoftRoadModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String bio;

    public String description;

    @OneToMany(mappedBy = "consultant")
    public Set<Consultation> consultations = new HashSet<>();

    @OneToMany(mappedBy = "consultant")
    public Set<Fee> fees = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "consultant_profile_categories",
            joinColumns = @JoinColumn(name = "consultant_profile_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    public Set<Category> categories = new HashSet<>();


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
}