package org.softRoad.exception;

import javax.ws.rs.core.Response;

public class DuplicateDataException extends SoftroadException
{
    public DuplicateDataException()
    {
        super("Duplicate Data");
    }

    public DuplicateDataException(String s)
    {
        super(s);
    }

    public DuplicateDataException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public DuplicateDataException(Throwable throwable)
    {
        super(throwable);
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.BAD_REQUEST;
    }
}
