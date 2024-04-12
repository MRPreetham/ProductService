package org.example.productservice.Exceptions;

public class InvalidRequestBodyException extends Exception{
    public InvalidRequestBodyException(String message){
        super(message);
    }
}
