package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "blog_article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;

    @Column(name = "header_image")
    private String headerImage;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "quote_text", columnDefinition = "TEXT")
    private String quoteText;

    @Column(name = "quote_author")
    private String quoteAuthor;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "read_time")
    private String readTime;

    @Column(name = "previous_article")
    private String previousArticle;

    @Column(name = "next_article")
    private String nextArticle;

    // Relationships
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogCategory> categories;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogSocialLink> socialLinks;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogTag> tags;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogComment> comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogGadget> gadgets;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogBottomParagraph> bottomParagraphs;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogResponsiveSection> responsiveSections;
}
