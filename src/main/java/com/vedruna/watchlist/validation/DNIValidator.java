package com.vedruna.watchlist.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DNIValidator implements ConstraintValidator<DNI, String> {

    private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

    @Override
    public void initialize(DNI constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            int number = Integer.parseInt(value.substring(0, 8));
            char character = value.charAt(8);

            int rm = number % 23;
            char dniChar = LETRAS.charAt(rm);

            return character == dniChar;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}