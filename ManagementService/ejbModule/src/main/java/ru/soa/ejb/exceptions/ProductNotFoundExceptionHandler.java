package ru.soa.ejb.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ProductNotFoundExceptionHandler implements ExceptionMapper<ProductNotFoundException> {

    @Override
    public Response toResponse(ProductNotFoundException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new RequestMessage(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
