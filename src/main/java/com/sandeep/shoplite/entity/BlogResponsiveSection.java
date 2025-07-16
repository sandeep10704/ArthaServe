package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "blog_responsive_section")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponsiveSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String title;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private BlogArticle article;

    @OneToMany(mappedBy = "responsiveSection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogResponsiveParagraph> paragraphs;
}
