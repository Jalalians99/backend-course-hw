package com.example.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.bookstore.domain.CategoryRepository;
import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;


@Controller
public class BookController {
    @Autowired
    private BookRepository repository; // Injecting the repository
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    // Show all books
    @RequestMapping(value = {"/", "/booklist"})
    public String bookList(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

    // Add new book
    @RequestMapping(value = "/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook";
    }

    // Save new book
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Book book) {
        repository.save(book);
        return "redirect:booklist";
    }

    // Delete book
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        repository.deleteById(bookId);
        return "redirect:../booklist";
    }

    // Edit book
    @RequestMapping(value = "/edit/{id}" , method = RequestMethod.GET)
    public String editBook(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute("book", repository.findById(bookId));
        model.addAttribute("categories", categoryRepository.findAll());
        return "editbook";
    }
    
}
