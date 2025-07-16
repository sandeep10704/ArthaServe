package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table
        (name = "blog_responsive_paragraph")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponsiveParagraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String paragraph;

    @ManyToOne
    @JoinColumn(name = "responsive_id")
    private BlogResponsiveSection responsiveSection;
}
