package com.switchfully.sharkitects.infrastructure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Infrastructure {
    private static final Logger logger = LoggerFactory.getLogger(Infrastructure.class);

    public static void inputValidation (boolean isInvalidInput, RuntimeException exception) {
        if (isInvalidInput) {
            logger.error(exception.getMessage());
            throw exception;
        }
    }

    public static boolean isNullEmptyOrBlank(String stringToCheck) {
        return stringToCheck == null || stringToCheck.isEmpty() || stringToCheck.isBlank();
    }

    public static boolean isNullEmptyOrBlank(int intToCheck) {
        return intToCheck <= 0;
    }

    public static boolean isNullEmptyOrBlank(double doubleToCheck) {
        return doubleToCheck <= 0;
    }
}
