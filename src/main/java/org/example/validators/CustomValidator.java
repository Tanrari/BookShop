package org.example.validators;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CustomValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
