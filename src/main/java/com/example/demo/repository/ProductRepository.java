package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Busca produtos por categoria
    List<Product> findByCategory(Category category);
    boolean existsByCategory(Category category);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.category.id = :categoryId")
    boolean existsByCategoryId(@Param("categoryId") Long categoryId);

    // SQL nativo: produtos abaixo de um preço máximo
    @Query(value = "SELECT * FROM products p WHERE p.price < :maxPrice", nativeQuery = true)
    List<Product> findProductsBelowMaxPrice(@Param("maxPrice") Double maxPrice);
}
