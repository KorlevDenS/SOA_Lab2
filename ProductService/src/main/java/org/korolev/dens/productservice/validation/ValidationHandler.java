package org.korolev.dens.productservice.validation;

import jakarta.validation.ConstraintViolationException;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.exceptions.ProductNotFoundException;
import org.korolev.dens.productservice.exceptions.ViolationOfUniqueFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        int last = ex.getBindingResult().getAllErrors().size();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ex.getBindingResult().getAllErrors().get(last - 1).getDefaultMessage()
        );
    }

    @ExceptionHandler(InvalidParamsException.class)
    public ResponseEntity<?> handleInvalidParamsException(InvalidParamsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ViolationOfUniqueFieldException.class)
    public ResponseEntity<?> handleViolationOfUniqueFieldException(ViolationOfUniqueFieldException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Внутренняя ошибка сервера. Попробуйте позже.");
//    }

}
