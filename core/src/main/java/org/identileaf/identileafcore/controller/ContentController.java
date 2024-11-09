package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }
    @GetMapping("/query")
    public String query() {
        return "query";
    }

    @GetMapping("/final-tree")
    public String finalTree() {return "final-tree";}
}
