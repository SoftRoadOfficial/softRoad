package org.softRoad.models.dao;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.quarkus.elytron.security.common.BcryptUtil;
import javax.validation.constraints.NotNull;
import org.eclipse.microprofile.config.ConfigProvider;

public class LoginUser
{
    @NotNull
    private String username;
    @NotNull
    private String password;

    public LoginUser(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public LoginUser()
    {
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    @JsonSetter
    public void setPassword(String password)
    {
        this.password = BcryptUtil.bcryptHash(password, 10,
                ConfigProvider.getConfig().getValue("bcryptHash.salt", String.class).getBytes());
    }

    @Override
    public String toString()
    {
        return "LoginUser{" + "username=" + username + ", password=" + password + '}';
    }

}
