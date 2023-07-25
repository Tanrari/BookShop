package org.example.validators;

import org.example.web.dto.UploadFile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadConverter implements Converter<CommonsMultipartFile, UploadFile> {
    @Override
    public UploadFile convert(CommonsMultipartFile source) {
        return new UploadFile(source);
    }
}
