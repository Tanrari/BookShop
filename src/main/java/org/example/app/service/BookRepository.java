package org.example.app.service;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {
    private final Logger logger = Logger.getLogger(BookRepository.class);
//    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retrieveAll() {
        List<Book> books = jdbcTemplate.query("Select * FROM books", new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setAuthor(rs.getString("author"));
                book.setTitle(rs.getString("title"));
                book.setSize(rs.getInt("size"));
                return book;
            }
        }
        );
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
//        book.setId(context.getBean(IdProvider.class).provideId(book));
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author",book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size",book.getSize());
        jdbcTemplate.update("INSERT into books(author,title,size) VALUES (:author, :title, :size)",parameterSource);
        logger.info("store new book:"+book);
//        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id",bookIdToRemove);
        jdbcTemplate.update("Delete from books WHERE id=:id",parameterSource);
//        for (Book book: retrieveAll()){
//            if (book.getId().equals(bookIdToRemove)){
//                logger.info("remove book completed:"+book);
////                return repo.remove(book);
//                return true;            }
      return true;
    }
    @Override
    public boolean removeByRegex(String regex){
      java.util.regex.Pattern pattern = Pattern.compile(".*"+regex+".*",Pattern.CASE_INSENSITIVE);

        for (Book book: retrieveAll()){
                Matcher matcher = pattern.matcher(book.getAuthor());
                if (matcher.find()) {
                    logger.info("remove book completed:" + book);
//                    repo.remove(book);
                    return true;
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
