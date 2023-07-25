package org.example.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UploadFileNotFoundValidator.class)
@Target(ElementType.PARAMETER)
public @interface FileFieldIsEmpty {
    String message() default "Invalid File";
    Class<?> [] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
