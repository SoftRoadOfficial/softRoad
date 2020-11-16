package org.softRoad.security;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.jwt.Claims;
import org.softRoad.models.User;

public class SecurityUtils {
    public static String createJwtToken(User user) {
        return Jwt
                .upn(user.username)
                .claim(Claims.phone_number.name(), user.phoneNumber)
                .claim(Claims.full_name.name(), user.displayName)
                .sign();
    }

    public static String hashPassword(String password) {
        return BcryptUtil.bcryptHash(password, 10,
                ConfigProvider.getConfig().getValue("bcryptHash.salt", String.class).getBytes());
    }
}
