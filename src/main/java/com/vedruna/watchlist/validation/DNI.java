package com.vedruna.watchlist.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = DNIValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DNI {
    String message() default "Invalid DNI"; //Mensaje de error 
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
