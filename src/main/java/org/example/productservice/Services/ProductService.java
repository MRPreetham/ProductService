package org.example.productservice.Services;

import org.example.productservice.Exceptions.CategoryNotFoundException;
import org.example.productservice.Exceptions.InvalidRequestBodyException;
import org.example.productservice.Exceptions.ProductNotCreatedException;
import org.example.productservice.Exceptions.ProductNotFoundException;
import org.example.productservice.Models.Category;
import org.example.productservice.Models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;
    List<Product> getAllProduct() throws ProductNotFoundException;
    Product insertNewProduct(Product product) throws ProductNotCreatedException;
    Product replaceProduct(Long id,Product product) throws ProductNotFoundException, InvalidRequestBodyException;
    Product updateProduct(Long id,Product product) throws ProductNotFoundException;
    Product deleteProduct(Long id) throws ProductNotFoundException;
    List<String> getAllCategory();
    List<Product> getProductByCategory(String name) throws CategoryNotFoundException;
}
