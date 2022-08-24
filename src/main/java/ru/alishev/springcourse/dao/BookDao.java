package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;

import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (name, year, author, person_id) VALUES(?, ?, ?, ?)", book.getName(), book.getYear(), book.getAuthor(), book.getPersonId());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET name=?, year=?, author=?, person_id = ? WHERE id=?", updatedBook.getName(), updatedBook.getYear(), updatedBook.getAuthor(), updatedBook.getPersonId(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public List<Book> personsBooks(int personId) {
        return (List<Book>) jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));

    }

    public void vacant(int bookId) {
        jdbcTemplate.update("UPDATE book set person_id = null where id = ?", bookId);
    }

    public void occupy(int book_id, int person_id) {
        jdbcTemplate.update("UPDATE book SET person_id = ? where id = ?", person_id, book_id);
    }
}
