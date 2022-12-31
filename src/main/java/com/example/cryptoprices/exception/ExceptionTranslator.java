package com.example.cryptoprices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        final Class<?> type = e.getRequiredType();
        final String message;
        assert type != null;
        if(type.isEnum()){
            message = "The parameter " + e.getName() + " must have a value among : " + Arrays.toString(type.getEnumConstants());
        }
        else{
            message = "The parameter " + e.getName() + " must be of type " + type.getTypeName();
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
