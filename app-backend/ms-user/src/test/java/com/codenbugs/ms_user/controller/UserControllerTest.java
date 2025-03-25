package com.codenbugs.ms_user.controller;

import com.codenbugs.ms_user.controllers.UserController;
import com.codenbugs.ms_user.dtos.UserReponseDto;
import com.codenbugs.ms_user.dtos.UserRequestDto;
import com.codenbugs.ms_user.models.User;
import com.codenbugs.ms_user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private UserReponseDto userReponseDto;

    @BeforeEach
    public void setup() {
        userReponseDto = new UserReponseDto(new User(1, "test", "", "test@gmail.com", ""));
    }

    @Test
    void testHelloWorld() throws Exception {
        mockMvc.perform(get("/v1/user")).andExpect(status().isOk());
    }

    @Test
    void testRegister() throws Exception {
        String json = "{\"id\": 1, \"username\": \"test\", \"email\": \"test@gmail.com\"}";
        when(userService.register(new UserRequestDto("test@gmail.com", "test", ""))).thenReturn(userReponseDto);
        mockMvc.perform(post("/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isCreated());
    }

}
