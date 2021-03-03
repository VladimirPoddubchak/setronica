package com.poddubchak.testtask.setronica.exception;

import java.util.NoSuchElementException;

public class NotFoundByCurrencyException extends NoSuchElementException {
    public NotFoundByCurrencyException(String s) {
        super(s);
    }
}
