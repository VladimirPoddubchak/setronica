package com.poddubchak.testtask.setronica.exception;

public class NoSuchCurrencyException extends IllegalArgumentException{
    public NoSuchCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
