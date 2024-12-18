package org.identileaf.identileafcore.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @InjectMocks
    RegisterService registerService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    public void testEmailRegex(){
        String goodEmail = "JohnDoe@gmail.com";
        String badEmail_NoAt = "Johngmail.com";
        String goodEmail_NoDomain = "John@gmailcom";

        assertTrue(registerService.validateNewEmail(goodEmail));
        assertFalse(registerService.validateNewEmail(badEmail_NoAt));
        assertFalse(registerService.validateNewEmail(goodEmail_NoDomain));
        assertFalse(registerService.validateNewEmail(null));
        assertFalse(registerService.validateNewEmail(""));
        assertFalse(registerService.validateNewEmail("@."));

    }
    @Test
    public void testPasswordRegex(){
        String goodPassword = "G00dP@$$w0rd";
        String badPassword_NotLongEnough = "P!23";
        String badPassword_NoNumbers = "p@ssword";
        String badPassword_NoLetters = "!2#4%6&8";
        String badPassword_NoSpecialCharacters = "passw0rd";

        assertTrue(registerService.validateNewPassword(goodPassword));
        assertFalse(registerService.validateNewPassword(badPassword_NotLongEnough));
        assertFalse(registerService.validateNewPassword(badPassword_NoNumbers));
        assertFalse(registerService.validateNewPassword(badPassword_NoLetters));
        assertFalse(registerService.validateNewPassword(badPassword_NoSpecialCharacters));
        assertFalse(registerService.validateNewPassword(null));
    }
}