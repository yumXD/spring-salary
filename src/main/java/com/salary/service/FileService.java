package com.salary.service;

import com.salary.domain.Employee;
import com.salary.domain.FileEntity;
import com.salary.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class FileService {
    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    public FileEntity saveFile(Employee employee, MultipartFile file) {

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String encodedFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1); // UTF-8 인코딩
        File dest = new File(fileDir + encodedFileName);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setOrgNm(file.getOriginalFilename());
        fileEntity.setSavedNm(encodedFileName);
        //fileEntity.setSavedPath(null);
        fileEntity.setEmployee(employee);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileRepository.save(fileEntity);
    }

    public void deleteFile(Long id) {
        FileEntity file = fileRepository.findByEmployeeId(id);
        // 파일 경로 생성
        Path filePath = Paths.get(fileDir).resolve(file.getSavedNm());

        // 파일 삭제
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
