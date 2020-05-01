package com.app.tictactoe.other.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, PasswordDetails> {
    @Override
    public boolean isValid(PasswordDetails passwordDetails, ConstraintValidatorContext constraintValidatorContext) {
        return passwordDetails.getPassword().equals(passwordDetails.getConfirmPassword());
    }

    @Override
    public void initialize(Password constraintAnnotation) {

    }
}
