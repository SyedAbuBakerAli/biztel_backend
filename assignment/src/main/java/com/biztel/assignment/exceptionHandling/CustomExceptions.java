package com.biztel.assignment.exceptionHandling;

public class CustomExceptions {

    public static class DatabaseAccessException extends RuntimeException {

        public DatabaseAccessException(final String message) {
            super(message);
        }
    }
}
