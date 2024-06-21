package com.sakan.sakan.service.Impl;

import com.sakan.sakan.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();

        String filePath = path + File.separator + filename;

        Path path1 = Paths.get(filePath);

        if(Files.exists(path1)){
            return filename;
        }

        Files.copy(file.getInputStream(), path1);

        return filename;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {

        String filePath = path + File.separator + filename;

        return new FileInputStream(filePath);
    }
}
