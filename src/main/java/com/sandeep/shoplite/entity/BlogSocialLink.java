package com.sandeep.shoplite.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blog_social_link")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogSocialLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private BlogArticle article;
}
