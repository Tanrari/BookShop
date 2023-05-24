package org.example.app.service;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;

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

    public boolean removeBookById(Integer bookIdToRemove) {
        if (bookIdToRemove!=null) {
            return bookRepo.removeItemById(bookIdToRemove);
        }
        return false;
    }
}
