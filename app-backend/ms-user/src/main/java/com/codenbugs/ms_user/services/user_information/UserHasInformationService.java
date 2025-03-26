package com.codenbugs.ms_user.services.user_information;

import com.codenbugs.ms_user.dtos.user_information.UserInformationCurrentRequest;
import com.codenbugs.ms_user.dtos.user_information.UserInformationRequestDto;
import com.codenbugs.ms_user.dtos.user_information.UserInformationResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


public interface UserHasInformationService {

     UserInformationResponseDto saveInformation(UserInformationRequestDto userRequestDto);

     UserInformationResponseDto updateInformation(UserInformationRequestDto userRequestDto) throws UserNotFoundException;

     UserInformationResponseDto getInformation(Integer userId);

     UserInformationResponseDto updateCurrentBalance(UserInformationCurrentRequest request) throws UserNotFoundException;

}
