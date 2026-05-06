package com.chirag.teamtaskmanager.exception;

import com.chirag.teamtaskmanager.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleUserNotFoundException(UserNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleProjectNotFoundException(ProjectNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleTaskNotFoundException(TaskNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleException(Exception ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(ex.getMessage());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}