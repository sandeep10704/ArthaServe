package com.sandeep.shoplite.dto;

import lombok.Data;
import java.util.List;

@Data
public class BlogPostsDTO {
    private String page;
    private List<Filter> filters;
    private List<Post> posts;
    private Pagination pagination;

    @Data
    public static class Filter {
        private String header;
        private List<String> topics;
    }

    @Data
    public static class Post {
        private Long id;
        private String cardHeading;
        private String textHeading;
        private String text;
        private String image;
        private List<String> tags;
        private String category;
        private List<String> socialLinks;
    }

    @Data
    public static class Pagination {
        private int itemsPerPage;
        private int totalPosts;
        private int pageCount;
    }
}
