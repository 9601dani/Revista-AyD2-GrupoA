package com.codenbugs.ms_user.service.labels;

import com.codenbugs.ms_user.dtos.label.LabelRequestDto;
import com.codenbugs.ms_user.dtos.label.LabelRequestUserDto;
import com.codenbugs.ms_user.dtos.label.LabelResponseDto;
import com.codenbugs.ms_user.dtos.user.LoginRequestDto;
import com.codenbugs.ms_user.dtos.user.UserRequestDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.labels.Label;
import com.codenbugs.ms_user.models.labels.UserHasLabel;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.label.LabelRepository;
import com.codenbugs.ms_user.repositories.label.UserHasLabelRepository;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import com.codenbugs.ms_user.services.labels.UserHasLabelService;
import com.codenbugs.ms_user.services.labels.UserHasLabelServiceImpl;
import com.codenbugs.ms_user.services.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserHasLabelTest {

    private UserHasLabelService userHasLabelService;

    @Mock
    private  UserHasLabelRepository userHasLabelRepository;

    @Mock
    private  LabelRepository labelRepository;

    @Mock
    private  UserRepository userRepository;

    private User user;

    private LabelRequestDto labelRequestDto;
    private LabelRequestUserDto labelRequestUserDto;
    private LabelResponseDto labelResponseDto;
    private Label label;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userHasLabelService = new UserHasLabelServiceImpl(userHasLabelRepository, labelRepository, userRepository);

        labelRequestDto = new LabelRequestDto("label_test");
        List<LabelRequestDto> list = new ArrayList<>();
        list.add(labelRequestDto);
        labelRequestUserDto = new LabelRequestUserDto(5, list);
        labelResponseDto = new LabelResponseDto(1, "label_test");

        label = new Label();
        label.setId(1);
        label.setName("label_test");

        user = new User();
        user.setId(1);
        user.setEmail("test@codenbugs.com");
        user.setPassword("password");
        user.setUsername("test");

    }

    @Test
    void savelLabelsForUserSuccesfully() throws UserNotFoundException {

        Map<String, String> expect = new HashMap<>();
        when(this.userRepository.findById(labelRequestUserDto.fkUser())).thenReturn(Optional.of(user));

        when(this.labelRepository.findLabelByName(labelRequestDto.name())).thenReturn(Optional.of(label));

        expect.put("message", "Cambios guardados correctamente");

        Map<String, String> actual = this.userHasLabelService.saveLabelsForUser(labelRequestUserDto);

        assertEquals(expect, actual);
    }

    @Test
    void savelLabelsForUserSuccesfullyWhitoutLabek() throws UserNotFoundException {

        Map<String, String> expect = new HashMap<>();
        when(this.userRepository.findById(labelRequestUserDto.fkUser())).thenReturn(Optional.of(user));

        when(this.labelRepository.findLabelByName(labelRequestDto.name())).thenReturn(Optional.empty());

        when(this.labelRepository.save(any(Label.class))).thenReturn(label);

        expect.put("message", "Cambios guardados correctamente");

        Map<String, String> actual = this.userHasLabelService.saveLabelsForUser(labelRequestUserDto);

        assertEquals(expect, actual);
    }

    @Test
    void savedLabelsNotFound() throws UserNotFoundException {

        Map<String, String> expect = new HashMap<>();
        when(this.userRepository.findById(labelRequestUserDto.fkUser())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> this.userHasLabelService.saveLabelsForUser(labelRequestUserDto));

    }

    @Test
    void getLabelsForUser(){
        List<LabelResponseDto> list = new ArrayList<>();
        list.add(labelResponseDto);

        when(this.userHasLabelRepository.findLabelsByUserId(user.getId())).thenReturn(list);

        List<LabelResponseDto> actual = this.userHasLabelService.getLabelsForUser(user.getId());

        assertEquals(list.size(), actual.size());
    }

    @Test
    void getAllLabels(){
        List<Label> labels = new ArrayList<>();
        labels.add(label);
        List<LabelResponseDto> list = new ArrayList<>();
        list.add(new LabelResponseDto(label));

        when(this.labelRepository.findAll()).thenReturn(labels);
        List<LabelResponseDto> actual = this.userHasLabelService.getAllLabels();

        assertEquals(list.size(), actual.size());
    }
}
