package com.poddubchak.testtask.setronica.exception;

public class IllegalIdException extends IllegalArgumentException{
    public IllegalIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalIdException(String s) {
        super(s);
    }
}
