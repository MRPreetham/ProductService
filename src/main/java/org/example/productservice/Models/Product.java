package org.example.productservice.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Product extends BaseModel implements Serializable {
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private String imageUrl;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Category category;
    private Long quantity;
}
