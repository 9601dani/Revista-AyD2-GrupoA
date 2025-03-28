package com.codenbugs.ms_user.services.user_information;

import com.codenbugs.ms_user.clients.UploadRestClient;
import com.codenbugs.ms_user.dtos.user_information.UserInformationCurrentRequest;
import com.codenbugs.ms_user.dtos.user_information.UserInformationRequestDto;
import com.codenbugs.ms_user.dtos.user_information.UserInformationResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.models.user_information.UserHasInformation;
import com.codenbugs.ms_user.repositories.user_information.UserHasInformationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class UserHasInformationServiceTest {

    private UserHasInformationService userHasInformationService;

    @Mock
    private UserHasInformationRepository userHasInformationRepository;

    @Mock
    private UploadRestClient uploadRestClient;

    private UserInformationRequestDto userInformationRequestDto;
    private UserInformationCurrentRequest addUserInformationCurrentRequest;
    private UserInformationCurrentRequest subtractUserInformationCurrentRequest;
    private UserHasInformation uhi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userHasInformationService = new UserHasInformationServiceImpl(userHasInformationRepository, uploadRestClient);

        userInformationRequestDto = new UserInformationRequestDto( "test", 18, "description", 1);
        addUserInformationCurrentRequest = new UserInformationCurrentRequest(1, true, BigDecimal.valueOf(10));
        subtractUserInformationCurrentRequest = new UserInformationCurrentRequest(1, false, BigDecimal.valueOf(10));

        uhi = new UserHasInformation();
        uhi.setId(1);
        uhi.setPhoto_path("path");
        uhi.setDescription("description");
        uhi.setName("test");
        uhi.setAge(18);
        uhi.setCurrent_balance(BigDecimal.valueOf(100));
        uhi.setUser(new User(1,"username", "password", "email", ""));


    }

    @Test
    void saveInformationSuccessfully() {

        when(this.userHasInformationRepository.save(any(UserHasInformation.class))).thenReturn(uhi);

        UserInformationResponseDto expect = new UserInformationResponseDto(uhi);

        UserInformationResponseDto actual = userHasInformationService.saveInformation(userInformationRequestDto);

        assertEquals(expect, actual);

    }

    @Test
    void updateInformationSuccesfully() throws UserNotFoundException {

        when(this.userHasInformationRepository.findByUser_Id(userInformationRequestDto.fkUser())).thenReturn(Optional.of(uhi));

        when(this.userHasInformationRepository.save(any(UserHasInformation.class))).thenReturn(uhi);

        UserInformationResponseDto expect = new UserInformationResponseDto(uhi);

        UserInformationResponseDto actual = this.userHasInformationService.updateInformation(userInformationRequestDto);

        assertEquals(expect, actual);
    }

    @Test
    void updateInformationNotFound() throws UserNotFoundException {

        when(this.userHasInformationRepository.findByUser_Id(userInformationRequestDto.fkUser())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> this.userHasInformationService.updateInformation(userInformationRequestDto));
    }

    @Test
    void getInformationSuccesfully() {
        when(this.userHasInformationRepository.findByUser_Id(userInformationRequestDto.fkUser())).thenReturn(Optional.of(uhi));
        UserInformationResponseDto expect = new UserInformationResponseDto(uhi);
        UserInformationResponseDto actual = this.userHasInformationService.getInformation(userInformationRequestDto.fkUser());
        assertEquals(expect, actual);
    }

    @Test
    void getInformationNotFound() {
        when(this.userHasInformationRepository.findByUser_Id(userInformationRequestDto.fkUser())).thenReturn(Optional.empty());

        UserInformationResponseDto expect = this.userHasInformationService.getInformation(userInformationRequestDto.fkUser());

        assertNull(expect);
    }

    @Test
    void addCurrentBalanceSuccesfully() throws UserNotFoundException {
        when(this.userHasInformationRepository.findByUser_Id(addUserInformationCurrentRequest.fkUser())).thenReturn(Optional.of(uhi));

        when(this.userHasInformationRepository.save(any(UserHasInformation.class))).thenReturn(uhi);
        UserInformationResponseDto actual = this.userHasInformationService.updateCurrentBalance(addUserInformationCurrentRequest);

        assertEquals(BigDecimal.valueOf(110), actual.current_balance());

    }
    @Test
    void subtractCurrentBalanceSuccesfully() throws UserNotFoundException {
        when(this.userHasInformationRepository.findByUser_Id(subtractUserInformationCurrentRequest.fkUser())).thenReturn(Optional.of(uhi));

        when(this.userHasInformationRepository.save(any(UserHasInformation.class))).thenReturn(uhi);
        UserInformationResponseDto actual = this.userHasInformationService.updateCurrentBalance(subtractUserInformationCurrentRequest);

        assertEquals(BigDecimal.valueOf(90), actual.current_balance());

    }
    @Test
    void updateCurrentBalanceNotFound() throws UserNotFoundException {
        when(this.userHasInformationRepository.findByUser_Id(subtractUserInformationCurrentRequest.fkUser())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> this.userHasInformationService.updateCurrentBalance(subtractUserInformationCurrentRequest));

    }

}