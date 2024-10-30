package org.identileaf.identileafcore.controller;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(NoHandlerFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("404"); // The name of your 404.html file
        model.addAttribute("errorMessage", "The page you are looking for does not exist.");
        return modelAndView;
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
