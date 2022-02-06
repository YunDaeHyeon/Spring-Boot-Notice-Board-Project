package com.noticeboard.controller;

import com.noticeboard.dto.UserDTO;
import com.noticeboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoticeBoardController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPageRedirect(){
        return "redirect:/login-page";
    }

    @GetMapping("/login-page")
    public String loginPage(){
        return "loginPage";
    }

    @GetMapping("/register-page")
    public String registerPage(){
        return "registerPage";
    }

    @PostMapping("/register-action")
    public String registerAction(UserDTO userDTO){
        userService.saveUser(userDTO);
        return "redirect:/login-page";
    }
}
