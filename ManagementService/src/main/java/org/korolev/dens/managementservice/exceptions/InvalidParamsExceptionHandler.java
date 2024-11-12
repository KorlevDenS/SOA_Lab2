package org.korolev.dens.managementservice.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidParamsExceptionHandler implements ExceptionMapper<InvalidParamsException> {

    @Override
    public Response toResponse(InvalidParamsException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new RequestMessage(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
