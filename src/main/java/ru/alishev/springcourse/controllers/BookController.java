package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.BookDao;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;

import javax.validation.Valid;

/**
 * @author Neil Alishev
 */
@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDao bookDao;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDao bookDao, PersonDAO personDAO) {
        this.bookDao = bookDao;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        Integer personId = bookDao.show(id).getPersonId();
        if (personId == null) {
            model.addAttribute("person", new Person());
        } else {
            model.addAttribute("person", personDAO.show(personId));
        }

        model.addAttribute("people", personDAO.index());
        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";

        bookDao.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDao.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";

        bookDao.update(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/vacant")
    public String vacant(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        bookDao.vacant(id);
        return "redirect:../../book/{id}";
    }

    @PatchMapping("/{id}/occupy")
    public String occupy(@ModelAttribute("person") Person person, @PathVariable("id") int bookId) {
        bookDao.occupy(bookId, person.getId());
        return "redirect:../../book/{id}";
    }
}
