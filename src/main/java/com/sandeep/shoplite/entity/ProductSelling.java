package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "productselling") // ✅ Explicit table name
@Getter
@Setter
public class ProductSelling {

    @Id
    private Long productid;

    private int sellingno;

    @OneToOne
    @JoinColumn(name = "productid", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;
}
