package org.korolev.dens.managementservice.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ProductServiceExceptionHandler implements ExceptionMapper<ProductServiceException> {

    @Override
    public Response toResponse(ProductServiceException e) {
        return Response
                .status(e.getStatus().getStatusCode())
                .entity(new RequestMessage(e.getStatus().getStatusCode(), e.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
