package com.example.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Test
    public void createNewCategory() {
        // Create and save a category
        Category category = new Category("Science");
        categoryRepository.save(category);
        
        // Verify category was saved with an ID
        assertThat(category.getCategoryid()).isNotNull();
    }
    
    @Test
    public void findByName() {
        // Create and save categories
        Category category1 = new Category("Horror");
        Category category2 = new Category("Romance");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        
        // Find category by name
        Category found = categoryRepository.findByName("Horror");
        
        // Verify correct category was found
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Horror");
    }
    
    @Test
    public void deleteCategory() {
        // Create and save a category
        Category category = new Category("To Delete");
        categoryRepository.save(category);
        
        // Verify category exists
        Long categoryId = category.getCategoryid();
        assertThat(categoryRepository.findById(categoryId)).isPresent();
        
        // Delete category
        categoryRepository.deleteById(categoryId);
        
        // Verify category no longer exists
        assertThat(categoryRepository.findById(categoryId)).isEmpty();
    }
} 