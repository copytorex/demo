package com.copytorex.restservices.exception;

public class DuplicateResourceFoundException extends RuntimeException {
    public DuplicateResourceFoundException(String message) {
        super(message);
    }
}
