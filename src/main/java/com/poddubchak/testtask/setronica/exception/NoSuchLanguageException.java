package com.poddubchak.testtask.setronica.exception;

public class NoSuchLanguageException extends IllegalArgumentException{
    public NoSuchLanguageException(String message, Throwable cause) {
        super(message, cause);
    }
}
