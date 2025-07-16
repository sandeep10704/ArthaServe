package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "blog_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private BlogArticle article;
}
