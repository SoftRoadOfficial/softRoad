package org.softRoad.exception;

import javax.ws.rs.core.Response;

public class ForbiddenException extends SoftroadException {
    public ForbiddenException() {
        super("Forbidden");
    }

    public ForbiddenException(String s) {
        super(s);
    }

    public ForbiddenException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ForbiddenException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.FORBIDDEN;
    }
}
