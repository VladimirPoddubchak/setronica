package com.poddubchak.testtask.setronica.exception;

import java.util.NoSuchElementException;

public class ProductNotFoundException extends NoSuchElementException {
    public ProductNotFoundException(String s) {
        super(s);
    }
}
