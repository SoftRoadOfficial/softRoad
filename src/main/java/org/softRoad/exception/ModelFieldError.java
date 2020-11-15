package org.softRoad.exception;

public class ModelFieldError
{
    private String field;
    private String message;

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "Error{" + "field=" + field + ", message=" + message + '}';
    }

}
