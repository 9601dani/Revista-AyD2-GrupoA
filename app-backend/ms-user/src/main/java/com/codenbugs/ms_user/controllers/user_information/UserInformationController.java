package com.codenbugs.ms_user.controllers.user_information;

import com.codenbugs.ms_user.dtos.module.ModuleResponseDto;
import com.codenbugs.ms_user.dtos.user_information.UserInformationResponseDto;
import com.codenbugs.ms_user.services.user.UserService;
import com.codenbugs.ms_user.services.user_information.UserHasInformationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
