package org.softRoad.exception;

import javax.ws.rs.core.Response;

public class InvalidDataException extends SoftroadException
{
    public InvalidDataException()
    {
        super("Invalid Data");
    }

    public InvalidDataException(String s)
    {
        super(s);
    }

    public InvalidDataException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public InvalidDataException(Throwable throwable)
    {
        super(throwable);
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.BAD_REQUEST;
    }
}
