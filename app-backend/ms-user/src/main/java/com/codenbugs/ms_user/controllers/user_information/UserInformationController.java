package com.codenbugs.ms_user.controllers.user_information;

import com.codenbugs.ms_user.dtos.module.ModuleResponseDto;
import com.codenbugs.ms_user.dtos.user_information.UserInformationCurrentRequest;
import com.codenbugs.ms_user.dtos.user_information.UserInformationPhotoRequest;
import com.codenbugs.ms_user.dtos.user_information.UserInformationRequestDto;
import com.codenbugs.ms_user.dtos.user_information.UserInformationResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.services.user.UserService;
import com.codenbugs.ms_user.services.user_information.UserHasInformationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserInformationController {

    private final UserHasInformationService uhiService;

    @GetMapping("/info/{id}")
    public ResponseEntity<UserInformationResponseDto> getInformation(@PathVariable Integer id) {
        UserInformationResponseDto response = this.uhiService.getInformation(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/info/update")
    public ResponseEntity<UserInformationResponseDto> updateInformation(@RequestBody UserInformationRequestDto request) throws UserNotFoundException {
        UserInformationResponseDto response = this.uhiService.updateInformation(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/info/update/photo_path/{id}")
    public ResponseEntity<HashMap<String, String>> updatePhotoPath(@PathVariable Integer id, @RequestPart("file") MultipartFile file) throws UserNotFoundException {
        HashMap <String, String> response = this.uhiService.updatePhotoPathUser(id, file);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/info/update/current_balance")
    public ResponseEntity<UserInformationResponseDto> updateCurrentBalance(@RequestBody UserInformationCurrentRequest request) throws UserNotFoundException {
        UserInformationResponseDto response = uhiService.updateCurrentBalance(request);
        return ResponseEntity.ok(response);
    }
}
