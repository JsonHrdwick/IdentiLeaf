package org.identileaf.identileafcore.controller;

import ch.qos.logback.core.model.Model;
import org.aspectj.lang.annotation.Before;
import org.identileaf.identileafcore.model.User;
import org.identileaf.identileafcore.model.UserDTO;
import org.identileaf.identileafcore.service.RegisterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.Mockito;


@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    String badEmail_NoAt = "bademail.com";
    String badEmail_NoDomain = "bad@email";
    String badPassword_NoNumber = "password!";
    String badPassword_NoSpecial = "password1";
    String badPassword_OnlyLetters = "password";
    String badPasswordMatching = "differentpassword";

    String goodEmail = "JohnDoe@gmail.com";
    String goodPassword = "G00dP@$$w0rd";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService registerService;


    @BeforeEach
    public void setUp() {
        // Mocking the email validation service method
        Mockito.when(registerService.validateNewEmail(badEmail_NoAt)).thenReturn(false);
        Mockito.when(registerService.validateNewEmail(badEmail_NoDomain)).thenReturn(false);
        Mockito.when(registerService.validateNewEmail(goodEmail)).thenReturn(true);
        Mockito.when(registerService.validateNewPassword(badPassword_NoNumber)).thenReturn(false);
        Mockito.when(registerService.validateNewPassword(badPassword_NoSpecial)).thenReturn(false);
        Mockito.when(registerService.validateNewPassword(badPassword_OnlyLetters)).thenReturn(false);
        Mockito.when(registerService.validateNewPassword(goodPassword)).thenReturn(true);
    }

    @Test
    @WithMockUser
    public void testRegister() throws Exception {

        performPost(badEmail_NoAt, goodPassword, goodPassword, "Invalid email address");
        performPost(badEmail_NoDomain, goodPassword, goodPassword, "Invalid email address");
        performPost(goodEmail, badPassword_NoNumber, badPassword_NoNumber, "Password must be at least 8 characters long with at least 1 number and 1 special character");
        performPost(goodEmail, badPassword_NoSpecial, badPassword_NoSpecial, "Password must be at least 8 characters long with at least 1 number and 1 special character");
        performPost(goodEmail, badPassword_OnlyLetters, badPassword_OnlyLetters, "Password must be at least 8 characters long with at least 1 number and 1 special character");
        performPost(goodEmail, goodPassword, badPasswordMatching, "Passwords do not match");

        mockMvc.perform(post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + goodEmail + "\"," +
                                "\"password\": \"" + goodPassword + "\"," +
                                "\"confirmPassword\": \"" + goodPassword + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Successfully registered!"));

        // This is not possible to integration test in the given context
        //performPost(goodEmail, goodPassword, goodPassword, "User already exists");

    }
    private void performPost(String email, String password1, String password2, String expectedValue) throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + email + "\"," +
                                "\"password\": \"" + password1 + "\"," +
                                "\"confirmPassword\": \"" + password2 + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error").value(expectedValue));
    }
}

