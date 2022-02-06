package com.noticeboard.controller;

import com.noticeboard.dto.BoardDTO;
import com.noticeboard.dto.UserDTO;
import com.noticeboard.service.BoardService;
import com.noticeboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class NoticeBoardController {

    // 로그인 & 회원가입 처리
    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

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

    @GetMapping("/board/list")
    public ModelAndView loginAction(Model model, Authentication authentication) throws Exception{
        // 로그인
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        model.addAttribute("message","아이디 : "+userDTO.getUserId()+", 권한 : "+ userDTO.getUserAuth());

        // 게시글
        ModelAndView mv = new ModelAndView("/board/list");
        List<BoardDTO> boardDTOList = boardService.boardInquire();
        mv.addObject("board",boardDTOList);

        return mv;
    }

    // 게시글 관리

}
