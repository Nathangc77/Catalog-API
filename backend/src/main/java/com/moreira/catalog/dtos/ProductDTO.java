package com.moreira.catalog.dtos;

import com.moreira.catalog.entities.Category;
import com.moreira.catalog.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class ProductDTO {

    private Long id;

    @Size(min = 5, max = 60, message = "The field must be between 5 and 60 characters")
    @NotBlank(message = "Required field")
    private String name;

    @NotBlank(message = "Required field")
    private String description;

    @Positive(message = "Price must be positive")
    private Double price;
    private String imgUrl;

    @PastOrPresent(message = "Invalid date")
    private Instant date;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        date = entity.getDate();
    }

    public ProductDTO(Product entity, Set<Category> categories) {
        this(entity);
        categories.forEach(x -> this.categories.add(new CategoryDTO(x)));
    }
}
