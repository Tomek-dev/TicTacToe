package com.app.tictactoe.other.validation;

import com.app.tictactoe.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {

    private UserDao userDao;

    @Autowired
    public UsernameValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void initialize(Username constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userDao.existsByUsername(s);
    }
}
