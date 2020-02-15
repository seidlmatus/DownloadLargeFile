package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class DownloadLargeFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(DownloadLargeFileApplication.class, args);
    }
}

@RestController
class BaseController {

    private static ClassPathResource classPathResource;
    private static HttpHeaders headers;

    {
        classPathResource = new ClassPathResource("5MB.zip");
        headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
    }

    @GetMapping(path = "/download")
    public ResponseEntity download(String param) throws IOException {

        InputStreamResource resource = new InputStreamResource(
                new FileInputStream(classPathResource.getFile()));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(classPathResource.getFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}

