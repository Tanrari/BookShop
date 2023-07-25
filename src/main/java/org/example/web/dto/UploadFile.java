package org.example.web.dto;
import org.example.validators.FileFieldIsEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class UploadFile implements MultipartFile {

    private final CommonsMultipartFile file;
    @Override
    public Resource getResource() {
        return file.getResource();
    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {
        file.transferTo(dest);
    }

    public UploadFile(CommonsMultipartFile file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return file.getOriginalFilename();
    }

    @Override
    public String getContentType() {
        return file.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return file.isEmpty();
    }

    @Override
    public long getSize() {
        return file.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return file.getBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return file.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        file.transferTo(dest);
    }
}
