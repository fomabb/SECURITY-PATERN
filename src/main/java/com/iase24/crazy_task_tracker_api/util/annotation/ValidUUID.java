package com.iase24.crazy_task_tracker_api.util.annotation;

import com.iase24.crazy_task_tracker_api.util.validation.UUIDMaskValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation of a UUID string like:
 * - 8-4-4-4-12
 * - only letters and digits
 */
@Documented
@Constraint(validatedBy = UUIDMaskValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {
    String massage() default "Invalid UUID format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
