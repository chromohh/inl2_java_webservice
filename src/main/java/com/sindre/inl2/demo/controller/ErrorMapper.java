package com.sindre.inl2.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ErrorMapper {

    private Map<String, String> generateObjectErrors(BindingResult result) {
        Map<String, String> fieldErrorMap = new HashMap<>();
        result.getGlobalErrors().forEach(objectError -> {
            if (objectError.getCode().contains("Category")) {
                fieldErrorMap.put("category", objectError.getDefaultMessage());
            }
        });
        return fieldErrorMap;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleNotFound(ResponseStatusException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("could not find");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleValidationErrors(ConstraintViolationException e){
        var errors = new ArrayList<String>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add((constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
