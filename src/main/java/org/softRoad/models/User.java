/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.softRoad.models;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.softRoad.config.Constants;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Email
    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX)
    public String email;

    @NotNull
    @Password
    public String password;

    @NotNull
    @Column(name = "phone_number", unique = true)
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

    public void setPassword(String password) {
        if (password != null && !password.isEmpty())
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", displayName='" + displayName + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
