package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "productdescriptionpoint")
public class ProductDescriptionPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String point;

    @ManyToOne
    @JoinColumn(name = "description_id")
    private ProductDescription productDescription;
}
