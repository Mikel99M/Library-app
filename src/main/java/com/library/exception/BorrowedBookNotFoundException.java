package com.library.exception;

public class BorrowedBookNotFoundException extends RuntimeException {
    public BorrowedBookNotFoundException(String message) {
        super(message);
    }
}
