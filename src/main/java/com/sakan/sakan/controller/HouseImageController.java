package com.sakan.sakan.controller;

import com.sakan.sakan.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/house-image")
@RequiredArgsConstructor
public class HouseImageController {

    private final FileService fileService;

    @Value("${project.house}")
    private String path;

    @GetMapping("/{filename}")
    public void serveFile(
            @PathVariable() String filename,
            HttpServletResponse response
    ) throws IOException {
        InputStream resourceFile = fileService.getResourceFile(path, filename);
        response.setContentType(MediaType.ALL_VALUE);
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}
