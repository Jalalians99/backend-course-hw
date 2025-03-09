package com.example.bookstore.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    // Return a single category by name
    Category findByName(String name);
}
