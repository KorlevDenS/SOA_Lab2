package ru.soa.ejb.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RequestMessage implements Serializable {

    private int code;
    private String message;
    private Object data;

     public RequestMessage(int statusCode, String message, Object data) {
        this.code = statusCode;
        this.message = message;
        this.data = data;
    }

    public RequestMessage(int statusCode, String message) {
        this.code = statusCode;
        this.message = message;
        this.data = null;
    }
}