package org.example.web.controllers;

import org.example.exceptions.BookShelfLoginException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/404")
    public String notFoundError(){
        return "errors/404";
    }

    @GetMapping("/FileNotFound")
    public String emptyFileError(){
        return "errors/FileNotFound";
    }

    @GetMapping("/BadRegex")
    public String badRegexError(){
        return "errors/BadRegex";
    }

}
