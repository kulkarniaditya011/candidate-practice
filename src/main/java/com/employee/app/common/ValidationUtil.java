package com.employee.app.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationUtil {
    private final Validator validator;

    public <T> void validate(T object){
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if(!violations.isEmpty()){
            return;
        }
        throw new ConstraintViolationException(violations);
    }

    public static boolean isBlank(CharSequence cs){
        return StringUtils.isEmpty(cs);
    }

    public static boolean isValidUrl(final String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

}
