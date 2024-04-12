package org.example.productservice.Repositories;

import org.example.productservice.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findById(Long id);
    Product save(Product product);
    List<Product> findAll();
    void deleteProductById(Long id);
    List<Product> findAllProductByCategoryTitle(String name);
}
