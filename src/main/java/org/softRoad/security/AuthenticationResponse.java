package org.softRoad.security;

public class AuthenticationResponse
{
    private String jwtToken;
    private String phoneNumber;

    public AuthenticationResponse(String jwtToken, String name)
    {
        this.jwtToken = jwtToken;
        this.phoneNumber = name;
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

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

}
