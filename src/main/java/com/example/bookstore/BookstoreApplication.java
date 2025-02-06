package com.example.bookstore;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

    private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
        }
    
    @Bean
    public CommandLineRunner loadData(BookRepository repository) {
        return args -> {
            log.info("save a couple of books");
            repository.save(new Book("1984", "George Orwell", 1949, "1234567890", 9.99));
            repository.save(new Book("To Kill a Mockingbird", "Harper Lee", 1960, "1234567891", 7.99));
            repository.save(new Book("A farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 5.99));
            repository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 6.99));
            // Add more sample books as needed
            log.info("fetch all books");
            for (Book book : repository.findAll()) {
                log.info(book.toString());
            }
        };
    }
}
