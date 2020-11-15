package org.softRoad.security;

public class AuthenticationResponse
{
    private String jwtToken;
    private String username;

    public AuthenticationResponse(String jwtToken, String name)
    {
        this.jwtToken = jwtToken;
        this.username = name;
    }

    public AuthenticationResponse()
    {
    }

    public String getJwtToken()
    {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken)
    {
        this.jwtToken = jwtToken;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

}
