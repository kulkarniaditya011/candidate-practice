package com.employee.app.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class LegalAgeValidator implements ConstraintValidator<LegalAge, LocalDate> {

    @Override
    public void initialize(LegalAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value==null) return false;

        return Period.between(value, LocalDate.now()).getYears()>=18;
    }
}
