package com.poddubchak.testtask.setronica.exception;

public class IllegalProductException extends IllegalArgumentException{
    public IllegalProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalProductException(String s) {
        super(s);
    }
}
