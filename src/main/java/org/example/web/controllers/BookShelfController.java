package org.example.web.controllers;
import org.apache.log4j.Logger;
import org.example.app.service.BookService;
import org.example.exceptions.RegexNotFoundException;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


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
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository contents:" + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }

    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, HttpServletRequest httpRequest, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByRegex")
    public String removeByRegex(@RequestParam(value = "queryRegex", required = false) String regex, @Autowired HttpServletRequest httpRequest) throws RegexNotFoundException {
        if (!(regex.equals(""))) {
            String value = regex.trim();
            for (int i = 0; i < bookService.getAllBooks().size(); i++) {
                if (bookService.getAllBooks().get(i).getAuthor().contains(value)) {
                    bookService.removeRegex(value);
                    logger.info("current repository contents:" + bookService.getAllBooks().size());
                }
            }
            return "redirect:/books/shelf";
        } else {
        throw new RegexNotFoundException();
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") UploadFile file, Model model) throws Exception {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            String name = file.getOriginalFilename();
                       byte[] bytes = file.getBytes();
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "external_uploads");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            logger.info("new file saved at:" + serverFile.getAbsolutePath());
        return "redirect:/books/shelf";
    }
    @ExceptionHandler({FileNotFoundException.class})
    public String handleError(Model model){
        model.addAttribute("errorUpload","File not Load");
        return "errors/FileNotFound";
    }


    @ExceptionHandler({RegexNotFoundException.class})
    public String handleRegexError(Model model){
        model.addAttribute("errorRegex","BadRegex");
        return "errors/BadRegex";
    }

}

