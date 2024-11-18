package org.identileaf.identileafcore.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codehaus.groovy.tools.shell.util.MessageSource;
import org.identileaf.identileafcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


    private final UserService userService;

    public CustomAuthenticationFailureHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = null;
        String username = request.getParameter("username");

        if (username != null) {
            // Call the processFailedLoginAttempt method
            userService.processFailedLoginAttempt(username);
        }

        // Determine the error message based on the type of exception
        if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "Your account is locked due to too many failed login attempts.";

        } else {
            errorMessage = "Invalid username or password!";
        }
        // Store the error message in the session for later use on the login page
        request.getSession().setAttribute("loginError", errorMessage);

        // Redirect to the login page with an error flag
        response.sendRedirect("/login?error=true");
    }
}