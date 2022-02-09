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

    // 로그인 성공 시 메인 페이지 이동 - 등급 : ROLE_USER
    @GetMapping("/board/list")
    public ModelAndView loginAction(Model model, Authentication authentication) throws Exception{
        User user = (User) authentication.getPrincipal();
        model.addAttribute("message","아이디 : "+user.getUsername()+", 권한 : "+ authentication.getAuthorities().toString());
        System.out.println(authentication.getAuthorities().toString());
        // 게시글
        ModelAndView mv = new ModelAndView("board/list");
        List<BoardDTO> boardDTOList = boardService.boardInquire();
        mv.addObject("board",boardDTOList);

        return mv;
    }

    // 로그인 성공 시 메인 페이지 이동 - 등급 : ROLE_ADMIN
    @GetMapping("/admin/list")
    public ModelAndView adminBoardListView(Model model, Authentication authentication) throws Exception{
        User user = (User) authentication.getPrincipal();
        model.addAttribute("message","아이디 : "+user.getUsername()+", 권한 : "+ authentication.getAuthorities().toString());
        System.out.println(authentication.getAuthorities().toString());
        // 게시글
        ModelAndView mv = new ModelAndView("admin/list");
        List<BoardDTO> boardDTOList = boardService.boardInquire();
        mv.addObject("admin",boardDTOList);

        return mv;
    }

    // 게시글 쓰기 페이지 이동 - 등급 : ROLE_USER
    @GetMapping("/board/write")
    public String boardWriteView() throws Exception {
        return "board/write";
    }

    // 게시글 쓰기 엑션 Method : POST
    @PostMapping("/board/board-insert")
    public String boardInsertAction(BoardDTO boardDTO) throws Exception{
        boardService.boardInsertAction(boardDTO);
        return "redirect:/board/list";
    }

    // 게시글 상세화면 이동 - 등급 : ROLE_USER
    @GetMapping("/board/read")
    public ModelAndView boardReadView(@RequestParam int boardNo) throws Exception{
        ModelAndView mv = new ModelAndView("board/read");
        BoardDTO boardDTO = boardService.boardReadAction(boardNo);
        mv.addObject("board",boardDTO);
        return mv;
    }

}
