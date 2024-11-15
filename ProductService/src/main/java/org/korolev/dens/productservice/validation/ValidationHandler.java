package org.korolev.dens.productservice.validation;

import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.exceptions.ProductNotFoundException;
import org.korolev.dens.productservice.exceptions.RequestMessage;
import org.korolev.dens.productservice.exceptions.ViolationOfUniqueFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        int last = ex.getBindingResult().getAllErrors().size();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new RequestMessage(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getBindingResult().getAllErrors().get(last - 1).getDefaultMessage()
                )
        );
    }

    @ExceptionHandler(InvalidParamsException.class)
    public ResponseEntity<?> handleInvalidParamsException(InvalidParamsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RequestMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(ViolationOfUniqueFieldException.class)
    public ResponseEntity<?> handleViolationOfUniqueFieldException(ViolationOfUniqueFieldException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new RequestMessage(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RequestMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        if (ex instanceof HttpMessageNotReadableException && ex.getMessage().contains("UnitOfMeasure")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestMessage(HttpStatus.BAD_REQUEST.value(),
                    "Поле unitOfMeasure должно иметь одним из значений: METERS, CENTIMETERS, MILLILITERS, GRAMS"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RequestMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Внутренняя ошибка сервера. Попробуйте позже."));
    }

}
