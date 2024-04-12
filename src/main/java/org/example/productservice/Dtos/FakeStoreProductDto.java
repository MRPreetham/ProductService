package org.example.productservice.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private double price;
    private String image;
}
