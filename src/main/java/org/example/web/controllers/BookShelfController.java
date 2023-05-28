package org.example.web.controllers;
import org.apache.log4j.Logger;
import org.example.app.service.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book){
            bookService.saveBook(book);
            logger.info("current repository contents:"+bookService.getAllBooks().size());
            return "redirect:/books/shelf";
    }
    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove" , required = false) String bookIdToRemove){
        System.out.println(bookIdToRemove);
        if (bookService.removeBookById(bookIdToRemove)){
            return "redirect:/books/shelf";
        }
        else {
            return "book_shelf";
        }
        }
    @PostMapping("/removeByRegex")
    public String removeByRegex(@RequestParam(value = "queryRegex",required = false) String regex){
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
