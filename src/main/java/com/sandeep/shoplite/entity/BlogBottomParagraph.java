package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "blog_bottom_paragraph")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogBottomParagraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String paragraph;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private BlogArticle article;
}
