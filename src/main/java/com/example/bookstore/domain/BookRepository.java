package com.example.bookstore.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BookRepository extends CrudRepository<Book, Long> {
       List<Book> findByAuthor(@Param("author") String author); 
        
    // Custom query to find books by title
    List<Book> findByTitle(String title);

    // Enabling ignore case for title
    List<Book> findByTitleIgnoreCase(String title);

    // Enabling ORDER BY for title
    List<Book> findByTitleOrderByTitleAsc(String title);
    
}
                     