package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.RolesValue;
import org.softRoad.security.Permission;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role extends SoftRoadModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    @RolesValue
    public String name;

    @ElementCollection
    @CollectionTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    public Set<Permission> permissions = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    public Set<User> users = new HashSet<>();

    public void setId(Integer id) {
        this.id = id;
        presentFields.add("id");
    }

    public void setName(String name) {
        this.name = name;
        presentFields.add("name");
    }
}
