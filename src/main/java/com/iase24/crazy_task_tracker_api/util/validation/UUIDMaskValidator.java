package com.iase24.crazy_task_tracker_api.util.validation;

import com.iase24.crazy_task_tracker_api.util.annotation.ValidUUID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UUIDMaskValidator implements ConstraintValidator<ValidUUID, String> {

    private static final String UUID_MASK_PATTERN =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(UUID_MASK_PATTERN);
    }
}
