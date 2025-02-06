package com.example.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;
import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

    private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
        }
    
    @Bean
    public CommandLineRunner loadData(BookRepository repository, CategoryRepository categoryRepository) {
        return args -> {
            log.info("save a couple of books");

            Category category1 = new Category("Fiction");
            Category category2 = new Category("Non-Fiction");
            Category category3 = new Category("Fantasy");
            Category category4 = new Category("Science Fiction");
            Category category5 = new Category("Horror");
            Category category6 = new Category("Romance");
            Category category7 = new Category("Mystery");
            Category category8 = new Category("Thriller");
            Category category9 = new Category("Biography");
            Category category10 = new Category("History");
            Category category11 = new Category("Self-Help");
            Category category12 = new Category("Cooking");

            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);
            categoryRepository.save(category4);
            categoryRepository.save(category5);
            categoryRepository.save(category6);
            categoryRepository.save(category7);
            categoryRepository.save(category8);
            categoryRepository.save(category9);
            categoryRepository.save(category10);
            categoryRepository.save(category11);
            categoryRepository.save(category12);


            repository.save(new Book("1984", "George Orwell", 1949, "1234567890", 9.99, category1));
            repository.save(new Book("To Kill a Mockingbird", "Harper Lee", 1960, "1234567891", 7.99, category3));
            repository.save(new Book("A farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 5.99, category8));
            repository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 6.99, category7));
            // Add more sample books as needed
            log.info("fetch all books");
            for (Book book : repository.findAll()) {
                log.info(book.toString());
            }
        };
    }
}
