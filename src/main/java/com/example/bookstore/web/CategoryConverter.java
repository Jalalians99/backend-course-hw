package com.example.bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;

@Component
public class CategoryConverter implements Converter<String, Category> {
    private static final Logger log = LoggerFactory.getLogger(CategoryConverter.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category convert(String source) {
        log.info("Converting string value '{}' to Category", source);
        
        if (source == null || source.isEmpty()) {
            return null;
        }
        
        try {
            // Try to parse as Long (categoryid)
            Long categoryId = Long.parseLong(source);
            log.info("Parsed category ID: {}", categoryId);
            return categoryRepository.findById(categoryId).orElse(null);
        } catch (NumberFormatException e) {
            // If not a number, try to handle other formats
            log.info("Value is not a number, attempting other parsing methods");
            
            // If the value contains "categoryid=", try to extract the ID
            if (source.contains("categoryid=")) {
                try {
                    int start = source.indexOf("categoryid=") + 11;
                    int end = source.indexOf(",", start);
                    if (end == -1) {
                        end = source.indexOf("]", start);
                    }
                    if (end > start) {
                        String idStr = source.substring(start, end);
                        Long id = Long.parseLong(idStr);
                        log.info("Extracted category ID from string: {}", id);
                        return categoryRepository.findById(id).orElse(null);
                    }
                } catch (Exception ex) {
                    log.error("Error parsing category from complex string: {}", ex.getMessage());
                }
            }
            
            log.warn("Could not convert string '{}' to Category", source);
            return null;
        }
    }
} 