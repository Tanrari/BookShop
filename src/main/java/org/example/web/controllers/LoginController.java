package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.service.LoginService;
import org.example.exceptions.BookShelfLoginException;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private Logger logger = Logger.getLogger(LoginController.class);
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(@Valid Model model){
        logger.info("GET /home returns login_page.html ");
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/auth")
    public String authenticate(LoginForm loginForm) throws BookShelfLoginException {
        if(loginService.authenticate(loginForm)){
            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        }else {
            logger.info("login Fail OK redirect to book shelf");
            throw  new BookShelfLoginException("invalid username or password");
        }

    }


}
