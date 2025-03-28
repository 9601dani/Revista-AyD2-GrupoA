package com.codenbugs.ms_user.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@FeignClient(name = "ms-upload", url = "http://localhost:8010/v1/uploads")
public interface UploadRestClient {
    @PostMapping(value = "/documents" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    HashMap<String, String> uploadFile(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/images" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    HashMap<String, String> uploadImage(@RequestParam("file") MultipartFile file);
}
