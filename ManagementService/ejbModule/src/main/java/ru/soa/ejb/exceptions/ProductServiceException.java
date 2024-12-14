package ru.soa.ejb.exceptions;


import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class ProductServiceException extends Exception {

    private final Response.Status status;

    public ProductServiceException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

}
