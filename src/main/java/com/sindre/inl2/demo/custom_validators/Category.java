package com.sindre.inl2.demo.custom_validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CategoryValidator.class})
public @interface Category {
    String message() default "Category does not exist or is empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
