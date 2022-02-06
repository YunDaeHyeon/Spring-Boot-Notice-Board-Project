package com.noticeboard.controller;

import com.noticeboard.dto.UserDTO;
import com.noticeboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/login-fail")
    public String loginFail(){
        return "loginFail";
    }

    @GetMapping("/login-success")
    public String loginAction(Model model, Authentication authentication){
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        model.addAttribute("message","아이디 : "+userDTO.getUserId()+", 권한 : "+ userDTO.getUserAuth());
        return "boardList";
    }
}
