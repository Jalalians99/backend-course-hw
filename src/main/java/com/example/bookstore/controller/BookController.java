package com.example.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Book;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookRepository repository;  // Injecting the repository
    @RequestMapping(value = {"/", "/booklist"})
    public String bookList(Model model) {
        List<Book> books = (List<Book>) repository.findAll();
        model.addAttribute("books", books);
        return "booklist";
    }
    @RequestMapping(value = "/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Book book) {
        repository.save(book);
        return "redirect:booklist";
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        repository.deleteById(bookId);
        return "redirect:../booklist";
    }
}
