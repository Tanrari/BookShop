package org.example.app.service;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {
    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("store new book:"+book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(String bookIdToRemove) {
        for (Book book: retrieveAll()){
            if (book.getId().equals(bookIdToRemove)){
                logger.info("remove book completed:"+book);
                return repo.remove(book);
            }
        }
        return false;
    }
    @Override
    public boolean removeByRegex(String regex){
      java.util.regex.Pattern pattern = Pattern.compile(".*"+regex+".*",Pattern.CASE_INSENSITIVE);

        for (Book book: retrieveAll()){
                Matcher matcher = pattern.matcher(book.getAuthor());
                if (matcher.find()) {
                    logger.info("remove book completed:" + book);
                    repo.remove(book);
                }
        }
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;

    }


    private void defaultInit(){
        logger.info("default INIT in book repo bean");

    }

    private void defaultDestroy(){
        logger.info("default DESTROY in book repo bean");
    }
}
