package com.noticeboard.controller;

import com.noticeboard.dto.BoardDTO;
import com.noticeboard.dto.UserDTO;
import com.noticeboard.service.BoardService;
import com.noticeboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String loginPage(@RequestParam(value = "error", required = false)String error,
                            Model model){
        model.addAttribute("error",error);
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
//        // 로그인
        User user = (User) authentication.getPrincipal();
        model.addAttribute("message","아이디 : "+user.getUsername()+", 권한 : "+ authentication.getAuthorities().toString());
        System.out.println(authentication.getAuthorities().toString());
        // 게시글
        ModelAndView mv = new ModelAndView("/board/list");
        List<BoardDTO> boardDTOList = boardService.boardInquire();
        mv.addObject("board",boardDTOList);

        return mv;
    }

    @GetMapping("/board/write")
    public String boardWriteView(){
        return "board/write";
    }

    // 게시글 관리

}
