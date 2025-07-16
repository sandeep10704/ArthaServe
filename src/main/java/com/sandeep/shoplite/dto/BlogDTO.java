package com.sandeep.shoplite.dto;

import lombok.Data;
import java.util.List;

@Data
public class BlogDTO {
    private Long id;
    private List<String> categories;
    private String previousArticle;
    private String nextArticle;
    private List<String> socialLinks;
    private List<String> tags;
    private List<Comment> comments;
    private ArticleData articleData;

    @Data
    public static class Comment {
        private String name;
        private String date;
        private String text;
        private String avatar;
    }

    @Data
    public static class ArticleData {
        private String title;
        private String category;
        private String headerImage;
        private String content;
        private Quote quote;
        private List<String> gadgetsList;
        private List<String> bottomParagraphs;
        private ResponsiveSection responsiveSection;
        private String updatedAt;
        private String readTime;
    }

    @Data
    public static class Quote {
        private String text;
        private String author;
    }

    @Data
    public static class ResponsiveSection {
        private String image;
        private String title;
        private List<String> paragraphs;
    }
}
