package org.softRoad.exception;

import javax.ws.rs.core.Response;

public class ServiceUnavailableException extends SoftroadException
{
    public ServiceUnavailableException()
    {
        super("Service Unavailable");
    }

    public ServiceUnavailableException(String s)
    {
        super(s);
    }

    public ServiceUnavailableException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public ServiceUnavailableException(Throwable throwable)
    {
        super(throwable);
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.SERVICE_UNAVAILABLE;
    }
}
