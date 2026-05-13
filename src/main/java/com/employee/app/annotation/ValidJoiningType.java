package com.employee.app.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidJoiningTypeValidator.class)
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidJoiningType {
    String message() default "Invalid Joining Type. Allowed values are: EARLY_JOINING, LATE_JOINING";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

