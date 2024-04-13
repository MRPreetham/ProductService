package org.example.productservice.Services;

import org.example.productservice.Dtos.FakeStoreProductDto;
import org.example.productservice.Exceptions.ProductNotCreatedException;
import org.example.productservice.Exceptions.ProductNotFoundException;
import org.example.productservice.Models.Category;
import org.example.productservice.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService{
    private final RestTemplate restTemplate;
    private final RedisTemplate redisTemplate;
    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate){
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }
    public Product convertFakeProduct(FakeStoreProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setImageUrl(productDto.getImage());
        product.setPrice(productDto.getPrice());
        Category category = new Category();
        product.setCategory(category);
        product.getCategory().setTitle(productDto.getCategory());
        return product;
    }
    public FakeStoreProductDto convertProduct(Product product){
        FakeStoreProductDto storeProductDto =  new FakeStoreProductDto();
        storeProductDto.setTitle(product.getTitle());
        storeProductDto.setImage(product.getImageUrl());
        storeProductDto.setDescription(product.getDescription());
        storeProductDto.setPrice(product.getPrice());
        if(product.getCategory()!=null){
            storeProductDto.setCategory(product.getCategory().getTitle());
        }
        return storeProductDto;
    }
    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {

        Product p = (Product) redisTemplate.opsForHash().get("Product","Product+"+id);
        Product p1 = (Product) redisTemplate.opsForValue().get("Product_"+id);

        if(p!=null){
            return p;
        }

        FakeStoreProductDto productDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/"+id,
                FakeStoreProductDto.class
        );
        if(productDto==null){
            throw new ProductNotFoundException(
                    "Product with id "+id+" doesnt exist"
            );
        }
        Product product = convertFakeProduct(productDto);

        redisTemplate.opsForHash().put("Product","Product_"+id,product);
        redisTemplate.opsForValue().set("Product_"+id,product);

        return product;
    }

    @Override
    public List<Product> getAllProduct() throws ProductNotFoundException {
        FakeStoreProductDto[] productDtoList = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );
        if(productDtoList==null){
            throw new ProductNotFoundException(
                    "Product List is empty"
            );
        }
        ArrayList<Product> productList =  new ArrayList<>();
        for(FakeStoreProductDto productDto:productDtoList){
            productList.add(convertFakeProduct(productDto));
        }
        return productList;
    }

    @Override
    public Product insertNewProduct(Product product) throws ProductNotCreatedException {
        FakeStoreProductDto productDto = convertProduct(product);
        FakeStoreProductDto responseDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                productDto,
                FakeStoreProductDto.class
        );
        if(responseDto==null){
            throw new ProductNotCreatedException(
                    "Could not create the product"
            );
        }
        return convertFakeProduct(responseDto);
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws ProductNotFoundException {
        FakeStoreProductDto storeProductDto = convertProduct(product);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(storeProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto productDto =  restTemplate.execute("https://fakestoreapi.com/products/"+id,
                HttpMethod.PUT, requestCallback, responseExtractor);
        if(productDto==null){
            throw new ProductNotFoundException(
                    "Product with id "+id+" doesnt exist"
            );
        }
        return convertFakeProduct(productDto);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFoundException {
        FakeStoreProductDto productDto = convertProduct(product);
        HttpEntity<FakeStoreProductDto> httpEntity = new HttpEntity<>(productDto);
        FakeStoreProductDto storeProductDto = restTemplate.patchForObject(
                "https://fakestoreapi.com/products/"+id,
                httpEntity,
                FakeStoreProductDto.class
        );
        if(storeProductDto==null){
            throw new ProductNotFoundException(
                    "Product with id "+id+" doesnt exist"
            );
        }
        return convertFakeProduct(storeProductDto);
    }

    @Override
    public Product deleteProduct(Long id) {
        RequestCallback requestCallback =
                restTemplate.httpEntityCallback(null, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto productDto = restTemplate.execute("https://fakestoreapi.com/products/"+id,
                HttpMethod.DELETE, requestCallback, responseExtractor);
        return convertFakeProduct(productDto);
    }

    @Override
    public List<String> getAllCategory() {
        String[] list = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );
        ArrayList<String> arrayList = new ArrayList<>();
        for(String l:list){
            arrayList.add(l);
        }
        return arrayList;
    }

    @Override
    public List<Product> getProductByCategory(String name) {
        FakeStoreProductDto[] productDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/"+name,
                FakeStoreProductDto[].class
        );
        ArrayList<Product> products = new ArrayList<>();
        for(FakeStoreProductDto productDto:productDtos){
            products.add(convertFakeProduct(productDto));
        }
        return products;
    }
}
