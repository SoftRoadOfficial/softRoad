package org.softRoad.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.ws.rs.core.Response;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DuplicateDataException.class, name = "DuplicateData"),
        @JsonSubTypes.Type(value = ForbiddenException.class, name = "Forbidden"),
        @JsonSubTypes.Type(value = BadRequestException.class, name = "BadRequest"),
        @JsonSubTypes.Type(value = InternalException.class, name = "Internal"),
        @JsonSubTypes.Type(value = InvalidDataException.class, name = "InvalidData"),
        @JsonSubTypes.Type(value = NotFoundException.class, name = "NotFound"),
        @JsonSubTypes.Type(value = ServiceUnavailableException.class, name = "ServiceUnavailable")
})
@JsonIgnoreProperties({"cause", "stackTrace", "suppressed"})
public class SoftroadException extends RuntimeException {
    public SoftroadException() {
    }

    public SoftroadException(String s) {
        super(s);
    }

    public SoftroadException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SoftroadException(Throwable throwable) {
        this("Softroad Exception", throwable);
    }

    public Response.Status getStatus() {
        return Response.Status.BAD_REQUEST;
    }
}
