package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Integer rating;
    private String description;
    private Integer stock;
    private String sku;
    private String brand;

    // --- Relationships ---

    // Product Images
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductImage> images;

    // Categories
    @ManyToMany
    @JoinTable(
            name = "productcategory",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    // Colors
    @ManyToMany
    @JoinTable(
            name = "productcolor",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private Set<Color> colors;

    // Sizes
    @ManyToMany
    @JoinTable(
            name = "productsize",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    private Set<Size> sizes;

    // Tags
    @ManyToMany
    @JoinTable(
            name = "producttag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    // --- New Relationships ---

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductDescription productDescription;

    // Reviews (1:N)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Review> reviews;
}
