package com.example.bookstore.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BookRestTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @BeforeEach
    public void setup() {
        // Clear existing data
        bookRepository.deleteAll();
        
        // Create test category
        Category fiction = new Category("Fiction");
        categoryRepository.save(fiction);
        
        // Add test books
        bookRepository.save(new Book("REST Test Book 1", "REST Author 1", 2020, "1234567890", 29.99, fiction));
        bookRepository.save(new Book("REST Test Book 2", "REST Author 2", 2021, "0987654321", 19.99, fiction));
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].title", is("REST Test Book 1")))
            .andExpect(jsonPath("$[1].title", is("REST Test Book 2")));
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetBookById() throws Exception {
        // Get the ID of the first book
        Long bookId = bookRepository.findByTitle("REST Test Book 1").get(0).getId();
        
        mockMvc.perform(get("/book/" + bookId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title", is("REST Test Book 1")))
            .andExpect(jsonPath("$.author", is("REST Author 1")));
    }
    
    @Test
    public void testGetBooksWithoutAuth() throws Exception {
        // Test accessing the API without authentication
        mockMvc.perform(get("/books"))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetBooksWithAdminRole() throws Exception {
        mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)));
    }
} 