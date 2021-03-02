package com.poddubchak.testtask.setronica.exception;

import java.util.NoSuchElementException;

public class NotFoundByLanguageOrCurrencyException extends NoSuchElementException {
    public NotFoundByLanguageOrCurrencyException(String s) {
        super(s);
    }
}
