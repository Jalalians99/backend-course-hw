package com.example.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;
import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.User;
import com.example.bookstore.domain.UserRepository;

@SpringBootApplication
public class BookstoreApplication {

    private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
    }
    
    @Bean
    @Transactional
    public CommandLineRunner loadData(BookRepository bookRepository, CategoryRepository categoryRepository) {
        return args -> {
            log.info("Checking if sample data needs to be loaded...");
            
            // Check if data already exists
            if (categoryRepository.count() == 0) {
                log.info("No categories found. Creating sample categories...");
                
                // Create categories
                Category fiction = new Category("Fiction");
                Category nonFiction = new Category("Non-Fiction");
                Category fantasy = new Category("Fantasy");
                Category scienceFiction = new Category("Science Fiction");
                Category horror = new Category("Horror");
                Category romance = new Category("Romance");
                Category mystery = new Category("Mystery");
                Category thriller = new Category("Thriller");
                Category biography = new Category("Biography");
                Category history = new Category("History");
                Category selfHelp = new Category("Self-Help");
                Category cooking = new Category("Cooking");
                
                // Save categories
                categoryRepository.save(fiction);
                categoryRepository.save(nonFiction);
                categoryRepository.save(fantasy);
                categoryRepository.save(scienceFiction);
                categoryRepository.save(horror);
                categoryRepository.save(romance);
                categoryRepository.save(mystery);
                categoryRepository.save(thriller);
                categoryRepository.save(biography);
                categoryRepository.save(history);
                categoryRepository.save(selfHelp);
                categoryRepository.save(cooking);
                
                log.info("Categories created successfully.");
            } else {
                log.info("Categories already exist, skipping category creation.");
            }
            
            // Check if books exist
            if (bookRepository.count() == 0) {
                log.info("No books found. Creating sample books...");
                
                // Get categories by name
                Category fiction = categoryRepository.findByName("Fiction");
                Category fantasy = categoryRepository.findByName("Fantasy");
                Category mystery = categoryRepository.findByName("Mystery");
                Category thriller = categoryRepository.findByName("Thriller");
                
                if (fiction != null && fantasy != null && mystery != null && thriller != null) {
                    log.info("Found required categories, creating books...");
                    
                    // Create books
                    Book book1 = new Book("1984", "George Orwell", 1949, "1234567890", 9.99, fiction);
                    Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, "1234567891", 7.99, fantasy);
                    Book book3 = new Book("A farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 5.99, thriller);
                    Book book4 = new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 6.99, mystery);
                    
                    // Save books
                    bookRepository.save(book1);
                    bookRepository.save(book2);
                    bookRepository.save(book3);
                    bookRepository.save(book4);
                    
                    log.info("Books created successfully.");
                    
                    // Log all books
                    log.info("All books in database:");
                    for (Book book : bookRepository.findAll()) {
                        log.info(book.toString());
                    }
                } else {
                    log.error("Could not find required categories. Book creation skipped.");
                    if (fiction == null) log.error("Fiction category not found");
                    if (fantasy == null) log.error("Fantasy category not found");
                    if (mystery == null) log.error("Mystery category not found");
                    if (thriller == null) log.error("Thriller category not found");
                }
            } else {
                log.info("Books already exist, skipping book creation.");
            }
        };
    }

    @Bean
    @Transactional
    public CommandLineRunner userDemo(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return (args) -> {
            log.info("Checking if users need to be created...");
            
            // Create admin user if not exists
            if (userRepository.findByUsername("admin") == null) {
                log.info("Creating admin user...");
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setEmail("admin@bookstore.com");
                adminUser.setRole("ADMIN");
                userRepository.save(adminUser);
                log.info("Admin user created successfully.");
            } else {
                log.info("Admin user already exists.");
            }
            
            // Create regular user if not exists
            if (userRepository.findByUsername("user") == null) {
                log.info("Creating regular user...");
                User regularUser = new User();
                regularUser.setUsername("user");
                regularUser.setPassword(passwordEncoder.encode("user"));
                regularUser.setEmail("user@bookstore.com");
                regularUser.setRole("USER");
                userRepository.save(regularUser);
                log.info("Regular user created successfully.");
            } else {
                log.info("Regular user already exists.");
            }
            
            log.info("User setup completed.");
        };
    }
}
