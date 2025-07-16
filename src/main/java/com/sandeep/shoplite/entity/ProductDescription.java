package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
@Table(name = "productdescription")
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "top_text") // ✅ Explicitly map DB column
    private String topText;

    @Column(name = "bottom_text") // ✅ Explicitly map DB column
    private String bottomText;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productDescription", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductDescriptionPoint> points = new ArrayList<>();
}
