package org.softRoad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.gradle.internal.impldep.com.google.common.base.Strings;
import org.softRoad.config.Constants;
import org.softRoad.models.query.QueryUtils;
import org.softRoad.security.SecurityUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@UserDefinition
public class User extends SoftRoadModel {

    @Transient
    public final static String ID = "id";
    @Transient
    public final static String EMAIL = "email";
    @Transient
    public final static String PASSWORD = "password";
    @Transient
    public final static String PHONE_NUMBER = "phone_number";
    @Transient
    public final static String DISPLAY_NAME = "display_name";
    @Transient
    public final static String ENABLED = "enabled";
    @Transient
    public final static String CITY = "city_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Email
    public String email;

    @NotNull
    @Password
    public String password;

    @NotNull
    @Column(name = "phone_number", unique = true)
    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX)
    @Username
    public String phoneNumber;

    @NotNull
    @Column(name = "display_name")
    public String displayName;

    public Boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL) //FIXME double-check cascadeType
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Roles
    public Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @JsonIgnore
    public City city;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    public Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    public Set<UpdateRequest> updateRequests = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    public Set<AuditLog> auditLogs = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    public Set<Procedure> procedures = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    public ConsultantProfile consultantProfile;

    public void setPassword(String password) {
        if (Strings.isNullOrEmpty(this.password))
            this.password = SecurityUtils.hashPassword(password);
        else
            this.password = password;
        this.presentFields.add("password");
    }

    public void setId(Integer id) {
        this.id = id;
        this.presentFields.add("id");
    }

    public void setEmail(String email) {
        this.email = email;
        this.presentFields.add("email");
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.presentFields.add("phoneNumber");
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        this.presentFields.add("displayName");
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
        this.presentFields.add("enabled");
    }

    public void setCity(City city) {
        this.city = city;
        presentFields.add("city");
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email='" + email + '\'' + ", password='" + password + '\'' + ", phoneNumber='"
                + phoneNumber + '\'' + ", displayName='" + displayName + '\'' + ", enabled=" + enabled + ", roles="
                + roles + '}';
    }

    public static String fields(String fieldName, String... fieldNames) {
        return QueryUtils.fields(User.class, fieldName, fieldNames);
    }
}
