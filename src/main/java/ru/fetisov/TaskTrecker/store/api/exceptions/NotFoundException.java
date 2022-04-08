package ru.fetisov.TaskTrecker.store.api.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
