package org.example.productservice.Repositories;

import org.example.productservice.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByTitle(String title);
    Category save(Category category);
    @Query("SELECT c.title from Category c")
    List<String> findAllTitle();
}
