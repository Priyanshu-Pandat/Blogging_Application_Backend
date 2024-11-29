package com.rrsh.blog.exceptions;

import com.rrsh.blog.model.Apiresponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Apiresponce> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
    String message = ex.getMessage();
    Apiresponce apiresponce = new Apiresponce(message , false );
    return  new ResponseEntity<Apiresponce>(apiresponce , HttpStatus.NOT_FOUND);
}
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String , String>> handleMethodNotValidException(MethodArgumentNotValidException ex) {
    Map<String ,String> resp = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName =  ((FieldError) error).getField();
      String message = error.getDefaultMessage();
        resp.put(fieldName, message);

    });
    return new ResponseEntity<Map<String , String>>(resp, HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(IOException.class)
    public ResponseEntity<Apiresponce> fileNotFound(IOException io) {
    String message = io.getMessage();
    Apiresponce apiresponce = new Apiresponce(message , false);
    return new ResponseEntity<>(apiresponce , HttpStatus.NOT_FOUND) ;
}
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Apiresponce> handleFileNotFound(FileNotFoundException ex) {
        String message = "File not found: " + ex.getMessage();
        Apiresponce apiresponce = new Apiresponce(message, false);
        return new ResponseEntity<>(apiresponce, HttpStatus.NOT_FOUND);
    }

}
