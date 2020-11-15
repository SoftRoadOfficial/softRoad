package org.softRoad.controllers;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.softRoad.security.AccessControlManager;

@Path("/softRoad")
@RequestScoped
public class HelloWorld
{
    @Inject
    JsonWebToken jwt;

    @Inject
    AccessControlManager accessControlManager;

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hello")
    public String hello(@Context SecurityContext ctx)
    {
        return getResponseString(ctx);
    }

    private String getResponseString(SecurityContext ctx)
    {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello + %s,"
                + " isHttps: %s,"
                + " authScheme: %s,"
                + " hasJWT: %s"
                + " phoneNumber: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt(), jwt.claim("phone_number"));
    }

    private boolean hasJwt()
    {
        return jwt.getClaimNames() != null;
    }
}
