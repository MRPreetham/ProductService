package org.example.productservice.Services;

import jakarta.transaction.Transactional;
import org.example.productservice.Exceptions.CategoryNotFoundException;
import org.example.productservice.Exceptions.InvalidRequestBodyException;
import org.example.productservice.Exceptions.ProductNotCreatedException;
import org.example.productservice.Exceptions.ProductNotFoundException;
import org.example.productservice.Models.Category;
import org.example.productservice.Models.Product;
import org.example.productservice.Repositories.CategoryRepository;
import org.example.productservice.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service("SelfProductService")
public class SelfProductService implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository){
       this.productRepository = productRepository;
       this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException(
                    "Product with id "+id+" doesn't exist"
            );
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProduct() throws ProductNotFoundException {
        return productRepository.findAll();
    }

    @Override
    public Product insertNewProduct(Product product) throws ProductNotCreatedException {
        Optional<Category> optionalCategory = categoryRepository.findByTitle(product.getCategory().getTitle());
        if(optionalCategory.isEmpty()){
            product.setCategory(categoryRepository.save(product.getCategory()));
        }else{
            product.setCategory(optionalCategory.get());
        }
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws ProductNotFoundException, InvalidRequestBodyException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException(
                    "Product with id "+id+" doesn't exist"
            );
        }
        Product saveProduct = optionalProduct.get();
        try{
            saveProduct.setTitle(product.getTitle());
            saveProduct.setImageUrl(product.getImageUrl());
            saveProduct.setDescription(product.getDescription());
            saveProduct.setPrice(product.getPrice());
            Optional<Category> optionalCategory = categoryRepository.findByTitle(product.getCategory().getTitle());
            if(optionalProduct.isEmpty()){
                saveProduct.setCategory(categoryRepository.save(product.getCategory()));
            }else{
                saveProduct.setCategory(optionalCategory.get());
            }
        }
        catch(Exception e){
            throw new InvalidRequestBodyException(
                    "Invalid request body"
            );
        }
        return productRepository.save(saveProduct);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException(
                    "Product with id "+id+" doesn't exist"
            );
        }
        Product saveProduct = optionalProduct.get();
        if(product.getTitle()!=null){
            saveProduct.setTitle(product.getTitle());
        }
        if(product.getImageUrl()!=null){
            saveProduct.setImageUrl(product.getImageUrl());
        }
        if(product.getDescription()!=null){
            saveProduct.setDescription(product.getDescription());
        }
        if(product.getPrice()!=null){
            saveProduct.setPrice(product.getPrice());
        }
        if(product.getCategory()!=null){
            Optional<Category> optionalCategory = categoryRepository.findByTitle(product.getCategory().getTitle());
            if(optionalProduct.isEmpty()){
                saveProduct.setCategory(categoryRepository.save(product.getCategory()));
            }else{
                saveProduct.setCategory(optionalCategory.get());
            }
        }
        return productRepository.save(saveProduct);
    }

    @Transactional
    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException(
                    "Product with id "+id+" doesn't exist"
            );
        }
        productRepository.deleteProductById(id);
        return optionalProduct.get();
    }

    @Override
    public List<String> getAllCategory() {
        return categoryRepository.findAllTitle();
    }

    @Override
    public List<Product> getProductByCategory(String name) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findByTitle(name);
        if(category.isEmpty()){
            throw new CategoryNotFoundException(
                    "Category with name "+name+" doesn't exist"
            );
        }
        return productRepository.findAllProductByCategoryTitle(name);
    }
}
