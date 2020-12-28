package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.security.jpa.RolesValue;
import org.hibernate.internal.util.xml.FilteringXMLEventReader;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.security.Permission;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends SoftRoadModel {
    @Transient
    public final static String ID = "id";
    @Transient
    public final static String NAME = "name";
    @Transient
    public final static String PERMISSIONS = "permission";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    @RolesValue
    @Column(name = "name", nullable = false, unique = true)
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

    public static String field(String fieldName) {
        return QueryUtils.field(Role.class, fieldName);
    }
}
