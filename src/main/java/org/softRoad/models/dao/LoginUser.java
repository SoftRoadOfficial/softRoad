package org.softRoad.models.dao;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.NotNull;

import org.softRoad.security.SecurityUtils;

public class LoginUser {
    @NotNull
    private String email;
    @NotNull
    private String password;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @JsonSetter
    public void setPassword(String password) {
        this.password = SecurityUtils.hashPassword(password);
    }

    @Override
    public String toString() {
        return "LoginUser{" + "username=" + email + ", password=" + password + '}';
    }

}
