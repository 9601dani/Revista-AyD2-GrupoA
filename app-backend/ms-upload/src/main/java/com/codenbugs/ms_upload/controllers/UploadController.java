package com.codenbugs.ms_upload.controllers;

import com.codenbugs.ms_upload.exceptions.NotCreatedException;
import com.codenbugs.ms_upload.services.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/uploads")
@AllArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/documents")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestPart("file") MultipartFile file) throws NotCreatedException {
        String objectName = this.uploadFile(file, "documents");
        Map<String, String> response = new HashMap<>();
        response.put("objectName", objectName);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/images")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) throws NotCreatedException {
        String objectName = this.uploadFile(file, "images");
        Map<String, String> response = new HashMap<>();
        response.put("objectName", objectName);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public String uploadFile(MultipartFile file, String path) throws NotCreatedException {
        return this.uploadService.uploadFile(file, path);
    }

}
