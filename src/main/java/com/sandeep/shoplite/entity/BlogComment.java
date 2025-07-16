package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "blog_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String date;

    @Column(columnDefinition = "TEXT")
    private String text;

    private String avatar;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private BlogArticle article;
}
