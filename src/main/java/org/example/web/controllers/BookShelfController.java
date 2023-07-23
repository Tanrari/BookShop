package org.example.web.controllers;
import org.apache.log4j.Logger;
import org.example.app.service.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfController {
    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;

    }

    @GetMapping("/shelf")
    public String books(Model model){
        logger.info("got book shelf");
        model.addAttribute("book",new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult , Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("book", book);
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository contents:"+bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }

    }
    @PostMapping("/remove")
    public String removeBook( @Valid BookIdToRemove bookIdToRemove , BindingResult bindingResult, HttpServletRequest httpRequest,  Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }
        else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByRegex")
    public String removeByRegex(@RequestParam(value = "queryRegex",required = false) String regex, @Autowired HttpServletRequest httpRequest){
        String value = regex.trim();
        for (int i=0; i<bookService.getAllBooks().size(); i++){
            if (bookService.getAllBooks().get(i).getAuthor().contains(value)){
                System.out.println(value);
                bookService.removeRegex(value);
                logger.info("current repository contents:"+bookService.getAllBooks().size());
            }
        }
        return "redirect:/books/shelf";
    }
}
