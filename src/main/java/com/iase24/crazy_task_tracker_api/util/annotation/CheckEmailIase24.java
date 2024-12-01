package com.iase24.crazy_task_tracker_api.util.annotation;

import com.iase24.crazy_task_tracker_api.util.validation.CheckEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckEmailValidator.class)
public @interface CheckEmailIase24 {

    String value() default "@iase24.com";

    String message() default "email must ends with iase24.com";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
