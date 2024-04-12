package org.example.productservice.Controllers;

import org.example.productservice.Exceptions.CategoryNotFoundException;
import org.example.productservice.Exceptions.InvalidRequestBodyException;
import org.example.productservice.Exceptions.ProductNotCreatedException;
import org.example.productservice.Exceptions.ProductNotFoundException;
import org.example.productservice.Models.Product;
import org.example.productservice.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(@Qualifier("FakeStoreProductService") ProductService productService){
        this.productService = productService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product>  getSingleProduct(@PathVariable("id") Long id)
            throws ProductNotFoundException {
        ResponseEntity<Product> response = new ResponseEntity<>(
                productService.getSingleProduct(id),
                HttpStatus.OK
        );
        return response;
    }
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() throws ProductNotFoundException {
        ResponseEntity<List<Product>> response = new ResponseEntity<>(
                productService.getAllProduct(),
                HttpStatus.OK
        );
        return response;
    }
    @PostMapping()
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product)
            throws ProductNotCreatedException {
        ResponseEntity<Product> response =  new ResponseEntity<>(
                productService.insertNewProduct(product),
                HttpStatus.OK
        );
        return response;
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody Product product)
            throws ProductNotFoundException, InvalidRequestBodyException {
        ResponseEntity<Product> response = new ResponseEntity<>(
                productService.replaceProduct(id,product),
                HttpStatus.OK
        );
        return response;
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product)
            throws ProductNotFoundException {
        ResponseEntity<Product> response =  new ResponseEntity<>(
                productService.updateProduct(id,product),
                HttpStatus.OK
        );
        return response;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        ResponseEntity<Product> response =  new ResponseEntity<>(
                productService.deleteProduct(id),
                HttpStatus.OK
        );
        return response;
    }
    @GetMapping("/categories")
    public ResponseEntity<List<String>>  getAllCategory(){
        ResponseEntity<List<String>> response = new ResponseEntity<>(
                productService.getAllCategory(),
                HttpStatus.OK
        );
        return response;
    }
    @GetMapping("/category/{name}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("name") String name)
            throws CategoryNotFoundException {
        ResponseEntity<List<Product>> response = new ResponseEntity<>(
                productService.getProductByCategory(name),
                HttpStatus.OK
                );
        return response;
    }
}
