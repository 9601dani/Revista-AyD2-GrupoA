package com.codenbugs.ms_user.services.user_information;

import com.codenbugs.ms_user.clients.UploadRestClient;
import com.codenbugs.ms_user.dtos.user_information.UserInformationCurrentRequest;
import com.codenbugs.ms_user.dtos.user_information.UserInformationPhotoRequest;
import com.codenbugs.ms_user.dtos.user_information.UserInformationRequestDto;
import com.codenbugs.ms_user.dtos.user_information.UserInformationResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.user_information.UserHasInformation;
import com.codenbugs.ms_user.repositories.user_information.UserHasInformationRepository;
import com.codenbugs.ms_user.services.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class UserHasInformationServiceImpl implements UserHasInformationService {

    private final UserHasInformationRepository userHasInformationRepository;
    private final UploadRestClient uploadRestClient;

    public UserInformationResponseDto saveInformation(UserInformationRequestDto userRequestDto) {

        UserHasInformation userHasInformation = new UserHasInformation();
        userHasInformation.setName(userRequestDto.name());
        userHasInformation.setAge(userRequestDto.age());
        userHasInformation.setDescription(userRequestDto.description());

        UserHasInformation uhi = userHasInformationRepository.save(userHasInformation);

        return new UserInformationResponseDto(uhi);
    }

    @Override
    public UserInformationResponseDto updateInformation(UserInformationRequestDto userRequestDto) throws UserNotFoundException {

        Optional<UserHasInformation> optionalUserHasInformation = userHasInformationRepository.findByUser_Id(userRequestDto.fkUser());
        if (optionalUserHasInformation.isEmpty()) {
            throw new UserNotFoundException("No hay información del usuario");
        }

        UserHasInformation userHasInformation = optionalUserHasInformation.get();
        userHasInformation.setName(userRequestDto.name());
        userHasInformation.setAge(userRequestDto.age());
        userHasInformation.setDescription(userRequestDto.description());
        UserHasInformation uhi = userHasInformationRepository.save(userHasInformation);
        return new UserInformationResponseDto(uhi);
    }

    @Override
    public UserInformationResponseDto getInformation(Integer userId) {
        Optional<UserHasInformation> optionalUserHasInformation = userHasInformationRepository.findByUser_Id(userId);
        if (optionalUserHasInformation.isEmpty()) {
            return null;
        }

        UserHasInformation userHasInformation = optionalUserHasInformation.get();
        return new UserInformationResponseDto(userHasInformation);
    }

    @Override
    public UserInformationResponseDto updateCurrentBalance(UserInformationCurrentRequest request) throws UserNotFoundException {
        Optional<UserHasInformation> optionalUserHasInformation = userHasInformationRepository.findByUser_Id( request.fkUser());
        if (optionalUserHasInformation.isEmpty()) {
            throw new UserNotFoundException("No hay información del usuario");
        }

        UserHasInformation userHasInformation = optionalUserHasInformation.get();
        BigDecimal current_balance = userHasInformation.getCurrent_balance();
        if(request.sum()){
            current_balance = current_balance.add(request.current_balance());
            userHasInformation.setCurrent_balance(current_balance);
        } else {
            current_balance = current_balance.subtract(request.current_balance());
            userHasInformation.setCurrent_balance(current_balance);
        }
        UserHasInformation uhi = userHasInformationRepository.save(userHasInformation);
        return new UserInformationResponseDto(uhi);
    }

    // Save user photo
    @Override
    public HashMap<String, String> updatePhotoPathUser(Integer fkUser, MultipartFile file) throws UserNotFoundException {
        Optional<UserHasInformation> optionalUserHasInformation = userHasInformationRepository.findByUser_Id(fkUser);
        if (optionalUserHasInformation.isEmpty()) {
            throw new UserNotFoundException("No hay información del usuario");
        }

        HashMap<String,String> path_saved = this.uploadRestClient.uploadImage(file);

        
        HashMap<String, String> path_saved_hash = new HashMap<>();
        UserHasInformation userHasInformation = optionalUserHasInformation.get();
        userHasInformation.setPhoto_path(path_saved.get(path_saved.getOrDefault(path_saved.keySet().iterator().next(), null)));
        UserHasInformation saved = this.userHasInformationRepository.save(userHasInformation);
        path_saved_hash.put("photo_path", saved.getPhoto_path());
        return path_saved_hash;

    }
}
