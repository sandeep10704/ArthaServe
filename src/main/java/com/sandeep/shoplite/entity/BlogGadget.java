package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "blog_gadget")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogGadget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gadget;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private BlogArticle article;
}
