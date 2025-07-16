package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "productdescription")
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String topText;
    private String bottomText;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productDescription", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductDescriptionPoint> points;

    public List<ProductDescriptionPoint> getPoints() {
        return points;
    }



}
