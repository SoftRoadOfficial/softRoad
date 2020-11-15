package org.softRoad.exception;

public class BadRequestException extends SoftroadException
{
    public BadRequestException()
    {
        super("Bad Request");
    }

    public BadRequestException(String s)
    {
        super(s);
    }

    public BadRequestException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public BadRequestException(Throwable throwable)
    {
        super(throwable);
    }
}
