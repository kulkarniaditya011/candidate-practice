package com.employee.app.annotation;

import com.employee.app.enums.JoiningType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidJoiningTypeValidator implements ConstraintValidator<ValidJoiningType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || JoiningType.isValid(value);
    }
}
