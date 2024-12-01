package com.iase24.crazy_task_tracker_api.util.validation;

import com.iase24.crazy_task_tracker_api.util.annotation.CheckEmailIase24;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckEmailValidator implements ConstraintValidator<CheckEmailIase24, String> {

    private String endOfEmail;

    @Override
    public void initialize(CheckEmailIase24 checkEmail) {
        endOfEmail = checkEmail.value();
    }

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext context) {
        return enteredValue.endsWith(endOfEmail);
    }
}
