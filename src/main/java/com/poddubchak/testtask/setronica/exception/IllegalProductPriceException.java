package com.poddubchak.testtask.setronica.exception;

public class IllegalProductPriceException extends IllegalArgumentException{
    public IllegalProductPriceException(String message, Throwable cause) {
        super(message, cause);
    }
}
