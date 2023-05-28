package org.example.app.service;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = Logger.getLogger(BookService.class);

    private void defaultInit(){
        logger.info("default INIT in book service");
    }

    private void defaultDestroy(){
        logger.info("default DESTROY in book service");
    }
    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks(){
        return bookRepo.retrieveAll();

    }


    public  void saveBook(Book book){
        if (!book.getAuthor().equals("")||!book.getTitle().equals("")) {
            bookRepo.store(book);
        }
    }

    public boolean removeRegex(String  rx) {
        if (rx!=null) {
            return bookRepo.removeByRegex(rx);
        }
        return false;
    }

    public boolean removeBookById(String bookIdToRemove) {
        if (bookIdToRemove!=null) {
            return bookRepo.removeItemById(bookIdToRemove);
        }
        return false;
    }


}
