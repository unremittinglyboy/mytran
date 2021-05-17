package com.example.boke.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NotFoundException extends RuntimeException{
    public NotFoundException(){
    }
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(String message, Throwable cause){
    super(message, cause);
    }

}
