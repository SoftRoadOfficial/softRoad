package org.softRoad.exception;

import javax.ws.rs.core.Response;

public class NotFoundException extends SoftroadException {
    public NotFoundException() {
        super("Not Found");
    }

    public NotFoundException(String s) {
        super(s);
    }

    public NotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotFoundException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.NOT_FOUND;
    }
}
