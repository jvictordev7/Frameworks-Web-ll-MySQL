package com.example.demo.repository;

import com.example.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // JPQL
    @Query("SELECT c FROM Category c WHERE c.name = :name")
    List<Category> findCategoryByName(@Param("name") String name);

    // SQL nativo
    @Query(value = "SELECT * FROM categories c WHERE c.name = :name", nativeQuery = true)
    List<Category> findCategoryByNameSQL(@Param("name") String name);
}
