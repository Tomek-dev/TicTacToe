package com.app.tictactoe.controller;

import com.app.tictactoe.other.dto.SignUpDto;
import com.app.tictactoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute("signUp", new SignUpDto());
        return "signUp";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute SignUpDto signUp){
        userService.create(signUp);
        return "redirect:/login";
    }
}
