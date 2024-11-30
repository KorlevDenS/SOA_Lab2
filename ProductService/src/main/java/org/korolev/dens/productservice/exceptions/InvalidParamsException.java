package org.korolev.dens.productservice.exceptions;

import java.io.IOException;

public class InvalidParamsException extends IOException {
    public InvalidParamsException(String message) {
        super(message);
    }
}
