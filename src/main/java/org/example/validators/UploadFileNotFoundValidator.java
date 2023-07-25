package org.example.validators;

import org.example.web.dto.UploadFile;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.io.IOException;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

public class UploadFileNotFoundValidator implements ConstraintValidator<FileFieldIsEmpty, UploadFile> {

    @Override
    public void initialize(FileFieldIsEmpty constraintAnnotation) {

    }

    @Override
    public boolean isValid(UploadFile value, ConstraintValidatorContext context) {

        if (value.isEmpty()){
            System.out.println("FFFFFFF");
            context.getDefaultConstraintMessageTemplate();

            return false;
        }
        else {
            System.out.println("AAAAAAA");
        }
        return true;

    }


}
