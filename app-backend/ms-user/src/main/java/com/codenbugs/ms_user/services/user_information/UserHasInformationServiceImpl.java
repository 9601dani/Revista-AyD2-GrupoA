package com.codenbugs.ms_user.services.user_information;

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

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class UserHasInformationServiceImpl implements UserHasInformationService {

    private final UserHasInformationRepository userHasInformationRepository;

    public UserInformationResponseDto saveInformation(UserInformationRequestDto userRequestDto) {

        UserHasInformation userHasInformation = new UserHasInformation();
        userHasInformation.setPhoto_path(userRequestDto.photo_path());
        userHasInformation.setName(userRequestDto.name());
        userHasInformation.setAge(userRequestDto.age());
        userHasInformation.setDescription(userRequestDto.description());
        userHasInformation.setCurrent_balance(0.0);

        UserHasInformation uhi = userHasInformationRepository.save(userHasInformation);

        return new UserInformationResponseDto(uhi);
    }

    @Override
    public UserInformationResponseDto updateInformation(UserInformationRequestDto userRequestDto) throws UserNotFoundException {

        Optional<UserHasInformation> optionalUserHasInformation = userHasInformationRepository.findByFkUser(userRequestDto.fkUser());
        if (optionalUserHasInformation.isEmpty()) {
            throw new UserNotFoundException("No hay información del usuario");
        }

        UserHasInformation userHasInformation = optionalUserHasInformation.get();
        userHasInformation.setPhoto_path(userRequestDto.photo_path());
        userHasInformation.setName(userRequestDto.name());
        userHasInformation.setAge(userRequestDto.age());
        userHasInformation.setDescription(userRequestDto.description());
        UserHasInformation uhi = userHasInformationRepository.save(userHasInformation);
        return new UserInformationResponseDto(uhi);
    }

    @Override
    public UserInformationResponseDto getInformation(Integer userId) {
        Optional<UserHasInformation> optionalUserHasInformation = userHasInformationRepository.findByFkUser(userId);
        if (optionalUserHasInformation.isEmpty()) {
            return null;
        }
        
        UserHasInformation userHasInformation = optionalUserHasInformation.get();
        return new UserInformationResponseDto(userHasInformation);
    }
}
