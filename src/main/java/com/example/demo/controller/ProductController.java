package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository,
                             CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // GET – listar todos
    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // GET /id – buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST – criar produto (com id da categoria)
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Category category = categoryRepository
                .findById(product.getCategory().getId())
                .orElse(null);

        if (category == null) {
            return ResponseEntity.badRequest().build();
        }

        product.setCategory(category);
        Product saved = productRepository.save(product);
        return ResponseEntity.ok(saved);
    }

    // PUT /id – atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable Long id,
            @RequestBody Product productData) {

        return productRepository.findById(id)
                .map(existing -> {
                    existing.setName(productData.getName());
                    existing.setPrice(productData.getPrice());

                    if (productData.getCategory() != null &&
                            productData.getCategory().getId() != null) {
                        categoryRepository.findById(productData.getCategory().getId())
                                .ifPresent(existing::setCategory);
                    }

                    Product updated = productRepository.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /id – apagar produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
