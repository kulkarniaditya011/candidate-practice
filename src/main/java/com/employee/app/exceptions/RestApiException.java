package com.employee.app.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RestApiException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final List<String> errorMessages= new ArrayList<>();

    public RestApiException(Object message, HttpStatus httpStatus){
        super(message.toString());
        this.httpStatus = httpStatus;
        this.errorMessages.add(message.toString());
    }

    public RestApiException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
        this.errorMessages.add(message);
    }

    public RestApiException(List<String> messages, HttpStatus httpStatus){
        super(messages.toString());
        this.httpStatus = httpStatus;
        this.errorMessages.addAll(messages);
    }
}
