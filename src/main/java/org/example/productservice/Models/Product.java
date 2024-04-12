package org.example.productservice.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private String imageUrl;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Category category;
    private Long quantity;
}
