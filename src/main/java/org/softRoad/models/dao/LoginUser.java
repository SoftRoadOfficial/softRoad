package org.softRoad.models.dao;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.NotNull;
import org.gradle.internal.impldep.com.google.common.base.Strings;

import org.softRoad.security.SecurityUtils;

public class LoginUser
{
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;

    public LoginUser(String phoneNumber, String password)
    {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public LoginUser()
    {
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword()
    {
        return password;
    }

    @JsonSetter
    public void setPassword(String password)
    {
        if (Strings.isNullOrEmpty(this.password))
            this.password = SecurityUtils.hashPassword(password);
        else
            this.password = password;
    }

    @Override
    public String toString()
    {
        return "LoginUser{" + "username=" + phoneNumber + ", password=" + password + '}';
    }

}
