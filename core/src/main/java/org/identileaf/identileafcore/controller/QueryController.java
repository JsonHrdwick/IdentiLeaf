package org.identileaf.identileaf.controller;

import org.identileaf.identileaf.model.UserDTO;
import org.identileaf.identileaf.service.AuthService;
import org.identileaf.identileaf.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class QueryController {
    @Autowired
    private QueryService queryService;

    /*@PostMapping("/question")
    public void question(Model model) {
        model.addAttribute("question", queryService.promptQuestion());
        ArrayList<String> answers = queryService.getAnswersNew();
        model.addAttribute("button1", answers.get(0));
        model.addAttribute("button2", answers.get(1));
        model.addAttribute("button3", answers.get(2));
    }*/

    @PostMapping("/answer")
    public String answer(Model model) {
        return "answer";
    }

    @GetMapping("/question")
    public ResponseEntity<Map<String, Object>> getQuestion() {
        Map<String, Object> response = new HashMap<>();
        response.put("question", queryService.promptQuestion());
        response.put("answers", queryService.getAnswersNew());
        return ResponseEntity.ok(response);
    }
}

