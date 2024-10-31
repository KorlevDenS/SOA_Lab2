package org.korolev.dens.productservice.exceptions;

public class ViolationOfUniqueFieldException extends Exception {
    public ViolationOfUniqueFieldException(String message) {
        super(message);
    }
}
