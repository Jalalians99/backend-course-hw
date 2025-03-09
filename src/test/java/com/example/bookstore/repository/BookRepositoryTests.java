package com.example.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;

@DataJpaTest
@ActiveProfiles("test")
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Test
    public void createNewBook() {
        // Create a category first
        Category category = new Category("Test Category");
        categoryRepository.save(category);
        
        // Create and save a book
        Book book = new Book("Test Book", "Test Author", 2023, "1234567890", 19.99, category);
        bookRepository.save(book);
        
        // Verify book was saved with an ID
        assertThat(book.getId()).isNotNull();
    }
    
    @Test
    public void findByTitle() {
        // Create a category
        Category category = new Category("Fiction");
        categoryRepository.save(category);
        
        // Create test books
        Book book1 = new Book("Harry Potter", "J.K. Rowling", 1997, "1234567890", 24.99, category);
        Book book2 = new Book("Lord of the Rings", "J.R.R. Tolkien", 1954, "0987654321", 19.99, category);
        
        // Save books
        bookRepository.save(book1);
        bookRepository.save(book2);
        
        // Search for books by title
        List<Book> found = bookRepository.findByTitleIgnoreCase("Harry Potter");
        
        // Verify correct book was found
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTitle()).isEqualTo("Harry Potter");
    }
    
    @Test
    public void findByAuthor() {
        // Create a category
        Category category = new Category("Fiction");
        categoryRepository.save(category);
        
        // Create test books
        Book book1 = new Book("Harry Potter", "J.K. Rowling", 1997, "1234567890", 24.99, category);
        Book book2 = new Book("Lord of the Rings", "J.R.R. Tolkien", 1954, "0987654321", 19.99, category);
        
        // Save books
        bookRepository.save(book1);
        bookRepository.save(book2);
        
        // Search for books by Tolkien
        List<Book> found = bookRepository.findByAuthor("J.R.R. Tolkien");
        
        // Verify correct book was found
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getAuthor()).isEqualTo("J.R.R. Tolkien");
    }
    
    @Test
    public void deleteBook() {
        // Create a category
        Category category = new Category("Non-Fiction");
        categoryRepository.save(category);
        
        // Create and save a book
        Book book = new Book("Test Delete", "Delete Author", 2020, "1122334455", 15.99, category);
        bookRepository.save(book);
        
        // Verify book exists
        Long bookId = book.getId();
        assertThat(bookRepository.findById(bookId)).isPresent();
        
        // Delete the book
        bookRepository.deleteById(bookId);
        
        // Verify book no longer exists
        assertThat(bookRepository.findById(bookId)).isEmpty();
    }
} 