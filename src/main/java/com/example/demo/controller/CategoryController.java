package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryController(CategoryRepository categoryRepository,
                              ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/byName/{categoryName}")
    public List<Category> getByName(@PathVariable String categoryName) {
        return categoryRepository.findCategoryByName(categoryName);
    }

    @GetMapping("/byNameSQL/{categoryName}")
    public List<Category> getByNameSQL(@PathVariable String categoryName) {
        return categoryRepository.findCategoryByNameSQL(categoryName);
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(
            @PathVariable Long id,
            @RequestBody Category category) {

        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(category.getName());
                    Category updated = categoryRepository.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    if (productRepository.existsByCategoryId(id)) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Existem produtos vinculados a essa categoria. Remova-os antes de excluir a categoria.");
                    }
                    try {
                        categoryRepository.delete(category);
                        return ResponseEntity.noContent().build();
                    } catch (DataIntegrityViolationException ex) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Não foi possível excluir: " + ex.getMostSpecificCause().getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
