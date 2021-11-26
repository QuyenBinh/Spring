package com.example.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomException {
    @ExceptionHandler(ExceptionValid.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerExceptionValid(ExceptionValid ex)    {

            return new ErrorResponse(ex.getMessage());
            
    }
}
