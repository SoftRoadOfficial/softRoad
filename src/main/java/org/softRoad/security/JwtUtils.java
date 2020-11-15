package org.softRoad.security;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.softRoad.models.User;

public class JwtUtils
{
    public static String createToken(User user)
    {
        return Jwt
                .upn(user.username)
                .claim(Claims.phone_number.name(), user.phoneNumber)
                .claim(Claims.full_name.name(), user.displayName)
                .sign();
    }
}
