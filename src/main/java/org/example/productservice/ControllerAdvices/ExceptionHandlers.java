package org.example.productservice.ControllerAdvices;

import org.example.productservice.Dtos.ExceptionDto;
import org.example.productservice.Exceptions.CategoryNotFoundException;
import org.example.productservice.Exceptions.InvalidRequestBodyException;
import org.example.productservice.Exceptions.ProductNotCreatedException;
import org.example.productservice.Exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    private final ExceptionDto exceptionDto;
    @Autowired
    public ExceptionHandlers(ExceptionDto exceptionDto){
        this.exceptionDto = exceptionDto;
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDto> ProductNotFoundException(ProductNotFoundException e){
        exceptionDto.setMessage(e.getMessage());
        ResponseEntity<ExceptionDto> response = new ResponseEntity<>(
                exceptionDto,
                HttpStatus.NOT_FOUND
        );
        return response;
    }
    @ExceptionHandler(ProductNotCreatedException.class)
    public ResponseEntity<ExceptionDto> ProductNotCreatedException(ProductNotCreatedException e){
        exceptionDto.setMessage(e.getMessage());
        ResponseEntity<ExceptionDto> response = new ResponseEntity<>(
                exceptionDto,
                HttpStatus.BAD_REQUEST
        );
        return response;
    }
    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseEntity<ExceptionDto> InvalidRequestBodyException(InvalidRequestBodyException e){
        exceptionDto.setMessage(e.getMessage());
        ResponseEntity<ExceptionDto> response = new ResponseEntity<>(
                exceptionDto,
                HttpStatus.BAD_REQUEST
        );
        return response;
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionDto> CategoryNotFoundException(CategoryNotFoundException e){
        exceptionDto.setMessage(e.getMessage());
        ResponseEntity<ExceptionDto> response = new ResponseEntity<>(
                exceptionDto,
                HttpStatus.NOT_FOUND
        );
        return response;
    }
}
