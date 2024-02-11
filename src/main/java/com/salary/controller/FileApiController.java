package com.salary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
@RequestMapping("/images")
@Slf4j
public class FileApiController {
    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get(fileDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // 파일이 존재하는지 확인
        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String contentType = Files.probeContentType(filePath);
        MediaType mediaType = MediaType.parseMediaType(contentType);

        log.info("mediaType: {}",mediaType);

        // 파일을 클라이언트로 전송
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
