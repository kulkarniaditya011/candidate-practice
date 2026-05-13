package com.employee.app.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LegalAgeValidator.class)
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LegalAge {
    String message() default "Age must be greater than 18";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
