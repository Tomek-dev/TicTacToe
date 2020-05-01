package com.app.tictactoe.other.validation;

import com.app.tictactoe.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private UserDao userDao;

    @Autowired
    public EmailValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userDao.existsByEmail(s);
    }

    @Override
    public void initialize(Email constraintAnnotation) {

    }
}
