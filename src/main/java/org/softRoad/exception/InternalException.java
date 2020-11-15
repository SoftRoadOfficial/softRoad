package org.softRoad.exception;


import javax.ws.rs.core.Response;

public class InternalException extends SoftroadException
{
    public InternalException()
    {
        super("Something went wrong");
    }

    public InternalException(String s)
    {
        super(s);
    }

    public InternalException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public InternalException(Throwable throwable)
    {
        super(throwable);
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.INTERNAL_SERVER_ERROR;
    }
}
