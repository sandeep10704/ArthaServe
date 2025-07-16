package com.sandeep.shoplite.entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "blog_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private BlogArticle article;
}
