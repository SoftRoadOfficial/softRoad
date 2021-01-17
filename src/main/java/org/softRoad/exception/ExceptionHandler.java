package org.softRoad.exception;

import java.sql.SQLException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception>
{
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception)
    {
        if (exception instanceof SoftroadException)
            return softroadException((SoftroadException) exception);

        if (exception instanceof PersistenceException) {
            if (exception.getCause() instanceof ConstraintViolationException) {
                SQLException e = ((ConstraintViolationException) exception.getCause()).getSQLException();
                return softroadException(new BadRequestException(e.getMessage()));
            }
            LOGGER.debug(exception);
            return Response.status(Status.BAD_REQUEST).entity(exception.toString()).build();
        }
        LOGGER.error(exception);
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }

    public Response softroadException(SoftroadException exception)
    {
        LOGGER.debug(exception);
        return Response.status(exception.getStatus()).entity(exception.getMessage()).build();
    }
}
