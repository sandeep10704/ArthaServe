package com.sandeep.shoplite.controllers;

import com.sandeep.shoplite.dto.BlogCommentDTO;
import com.sandeep.shoplite.dto.BlogDTO;
import com.sandeep.shoplite.dto.BlogPostsDTO;
import com.sandeep.shoplite.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogControllers {

    private final BlogService blogService;

    // ✅ Get single blog by id
    @GetMapping("/{id}")
    public BlogDTO getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    // ✅ Get all blogs (posts list with filters and pagination)
    @GetMapping
    public BlogPostsDTO getAllBlogs() {
        return blogService.getAllPosts();
    }
    // ✅ More specific route first
    @GetMapping("/posts/sorted")
    public List<Map<String, Object>> getPostsSortedByUpdatedAt() {
        return blogService.getPostsSortedByUpdatedAt();
    }
    @PostMapping("/{id}/comments")
    public BlogCommentDTO addComment(@PathVariable Long id, @RequestBody BlogCommentDTO commentDTO) {
        return blogService.addComment(id, commentDTO);
    }
    @PostMapping
    public BlogDTO addBlog(@RequestBody BlogDTO blogDTO) {
        return blogService.addBlog(blogDTO);
    }

}
