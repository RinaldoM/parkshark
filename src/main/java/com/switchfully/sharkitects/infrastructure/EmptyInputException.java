package com.switchfully.sharkitects.infrastructure;

public class EmptyInputException extends RuntimeException {
    public EmptyInputException(String label) {
        super("Empty " + label);
    }
}
