package com.employee.app.exceptions;

import com.employee.app.response.RestApiResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestApiResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request){
        String paramName = ex.getName();
        String expectedType = ex.getRequiredType() !=null ? ex.getRequiredType().getSimpleName():"unknown";
        String message = String.format("Invalid value for parameter '%s': must be of type %s.", paramName, expectedType);
        return new ResponseEntity<>(RestApiResponse.builder().message(message).timeStamp(new Date())
                .errors(request.getDescription(false)).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestApiResponse<Object>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            WebRequest request){

        Throwable rootCause = ex.getMostSpecificCause();
        String message = "Invalid request Payload";

        if(rootCause instanceof InvalidFormatException invalidFormatEx){
            String fieldName = invalidFormatEx.getPathReference();
            String targetType = invalidFormatEx.getTargetType().getSimpleName();
            Object value = invalidFormatEx.getValue();
            message = String.format("Invalid value %s for field %s. Expected type: %s",fieldName,targetType,value);
        } else if (rootCause instanceof MismatchedInputException MisMatchedEx) {
            message = "JSON structure is incorrect or missing correct fields: "+MisMatchedEx.getPathReference();
        } else{
            message= rootCause.getMessage();
        }
        return new ResponseEntity<>(RestApiResponse.builder()
                .message("invalid input").timeStamp(new Date()).errors(message).build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestApiResponse<Object>> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request){
        return new ResponseEntity<>(RestApiResponse.builder()
                .message("Static resource not found").timeStamp(new Date()).errors(request.getDescription(false)).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestApiResponse<Object>> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> cv: ex.getConstraintViolations()){
            errors.add(cv.toString());
        }
        return new ResponseEntity<>(RestApiResponse.builder()
                .message(errors).timeStamp(new Date()).errors(request.getDescription(false)).build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestApiResponse<Object>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request){
        return new ResponseEntity<>(RestApiResponse.builder()
                .message(ex.getMessage()).timeStamp(new Date()).errors(request.getDescription(false)).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        List<String> err= new ArrayList<>();
        for (FieldError error: ex.getBindingResult().getFieldErrors()){
            err.add(error.getDefaultMessage());
        }
        Collections.sort(err);
        return new ResponseEntity<>(RestApiResponse.builder()
                .message(err).timeStamp(new Date()).errors(request.getDescription(false)).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<RestApiResponse<Object>> handleRestApiException(RestApiException ex, WebRequest request){
        return new ResponseEntity<>(RestApiResponse.builder()
                .message(ex.getErrorMessages()).timeStamp(new Date()).errors(request.getDescription(false)).build(), HttpStatus.BAD_REQUEST);
    }
}

