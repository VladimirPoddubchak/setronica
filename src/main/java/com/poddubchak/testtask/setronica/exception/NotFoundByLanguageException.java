package com.poddubchak.testtask.setronica.exception;

import java.util.NoSuchElementException;

public class NotFoundByLanguageException extends NoSuchElementException {
    public NotFoundByLanguageException(String s) {
        super(s);
    }
}
