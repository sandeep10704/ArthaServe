package com.sandeep.shoplite.services;

import com.sandeep.shoplite.dto.BlogCommentDTO;
import com.sandeep.shoplite.dto.BlogDTO;
import com.sandeep.shoplite.dto.BlogPostsDTO;
import com.sandeep.shoplite.entity.*;
import com.sandeep.shoplite.repository.BlogArticleRepository;
import com.sandeep.shoplite.repository.BlogCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogArticleRepository blogArticleRepository;
    private final BlogCommentRepository blogCommentRepository;

    public BlogDTO getBlogById(Long id) {
        BlogArticle article = blogArticleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));

        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(article.getId());
        blogDTO.setCategories(article.getCategories().stream()
                .map(BlogCategory::getCategory).collect(Collectors.toList()));

        // Previous Article
        Optional<BlogArticle> previous = blogArticleRepository.findFirstByIdLessThanOrderByIdDesc(id);
        if (previous.isPresent()) {
            blogDTO.setPreviousArticle(previous.get().getTitle());
        } else {
            blogDTO.setPreviousArticle(null); // or empty string
        }

        // Next Article
        Optional<BlogArticle> next = blogArticleRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
        if (next.isPresent()) {
            blogDTO.setNextArticle(next.get().getTitle());
        } else {
            blogDTO.setNextArticle(null); // or empty string
        }

        blogDTO.setSocialLinks(article.getSocialLinks().stream()
                .map(BlogSocialLink::getLink).collect(Collectors.toList()));
        blogDTO.setTags(article.getTags().stream()
                .map(BlogTag::getTag).collect(Collectors.toList()));

        // Comments
        List<BlogDTO.Comment> commentList = article.getComments().stream().map(comment -> {
            BlogDTO.Comment c = new BlogDTO.Comment();
            c.setName(comment.getName());
            c.setDate(comment.getDate());
            c.setText(comment.getText());
            c.setAvatar(comment.getAvatar());
            return c;
        }).collect(Collectors.toList());
        blogDTO.setComments(commentList);

        // ArticleData
        BlogDTO.ArticleData articleData = new BlogDTO.ArticleData();
        articleData.setTitle(article.getTitle());
        articleData.setCategory(article.getCategory());
        articleData.setHeaderImage(article.getHeaderImage());
        articleData.setContent(article.getContent());
        articleData.setUpdatedAt(article.getUpdatedAt());
        articleData.setReadTime(article.getReadTime());

        // Quote
        BlogDTO.Quote quote = new BlogDTO.Quote();
        quote.setText(article.getQuoteText());
        quote.setAuthor(article.getQuoteAuthor());
        articleData.setQuote(quote);

        // Gadgets
        articleData.setGadgetsList(article.getGadgets().stream()
                .map(BlogGadget::getGadget).collect(Collectors.toList()));

        // Bottom Paragraphs
        articleData.setBottomParagraphs(article.getBottomParagraphs().stream()
                .map(BlogBottomParagraph::getParagraph).collect(Collectors.toList()));

        // Responsive Section (assuming one section)
        if (!article.getResponsiveSections().isEmpty()) {
            BlogResponsiveSection section = article.getResponsiveSections().get(0);
            BlogDTO.ResponsiveSection rs = new BlogDTO.ResponsiveSection();
            rs.setImage(section.getImage());
            rs.setTitle(section.getTitle());
            rs.setParagraphs(section.getParagraphs().stream()
                    .map(BlogResponsiveParagraph::getParagraph).collect(Collectors.toList()));
            articleData.setResponsiveSection(rs);
        }

        blogDTO.setArticleData(articleData);
        return blogDTO;
    }


    public BlogPostsDTO getAllPosts() {
        BlogPostsDTO dto = new BlogPostsDTO();
        dto.setPage("PostsLayout");

        List<BlogArticle> articles = blogArticleRepository.findAll();

        // ✅ Dynamically build Categories filter
        Set<String> categorySet = articles.stream()
                .flatMap(a -> a.getCategories().stream())
                .map(BlogCategory::getCategory)
                .collect(Collectors.toSet());
        categorySet.add("All"); // Add "All" at start if needed
        List<String> categoriesList = new ArrayList<>(categorySet);
        Collections.sort(categoriesList);

        BlogPostsDTO.Filter categories = new BlogPostsDTO.Filter();
        categories.setHeader("Categories");
        categories.setTopics(categoriesList);

        // ✅ Dynamically build Tags filter
        Set<String> tagSet = articles.stream()
                .flatMap(a -> a.getTags().stream())
                .map(BlogTag::getTag)
                .collect(Collectors.toSet());
        List<String> tagsList = new ArrayList<>(tagSet);
        Collections.sort(tagsList);

        BlogPostsDTO.Filter tags = new BlogPostsDTO.Filter();
        tags.setHeader("Tags");
        tags.setTopics(tagsList);

        // ✅ Dynamically build Social Links filter
        Set<String> socialLinkSet = articles.stream()
                .flatMap(a -> a.getSocialLinks().stream())
                .map(BlogSocialLink::getLink)
                .collect(Collectors.toSet());
        List<String> socialLinksList = new ArrayList<>(socialLinkSet);
        Collections.sort(socialLinksList);

        BlogPostsDTO.Filter socialLinks = new BlogPostsDTO.Filter();
        socialLinks.setHeader("Social links");
        socialLinks.setTopics(socialLinksList);

        dto.setFilters(List.of(categories, tags, socialLinks));

        // ✅ Posts from blog_article table
        List<BlogPostsDTO.Post> posts = articles.stream().map(article -> {
            BlogPostsDTO.Post post = new BlogPostsDTO.Post();
            post.setId(article.getId());
            post.setCardHeading(article.getCategory());
            post.setTextHeading(article.getTitle());
            post.setText(article.getContent().length() > 200 ? article.getContent().substring(0, 200) + "..." : article.getContent());
            post.setImage(article.getHeaderImage());

            // Tags
            post.setTags(article.getTags().stream()
                    .map(BlogTag::getTag)
                    .collect(Collectors.toList()));

            // Category (first category or fallback)
            String category = article.getCategories().isEmpty() ? "General" :
                    article.getCategories().get(0).getCategory();
            post.setCategory(category);

            // Social links
            post.setSocialLinks(article.getSocialLinks().stream()
                    .map(BlogSocialLink::getLink)
                    .collect(Collectors.toList()));

            return post;
        }).collect(Collectors.toList());

        dto.setPosts(posts);

        // ✅ Pagination
        BlogPostsDTO.Pagination pagination = new BlogPostsDTO.Pagination();
        pagination.setItemsPerPage(9);
        pagination.setTotalPosts(posts.size());
        pagination.setPageCount((int) Math.ceil((double) posts.size() / 9));
        dto.setPagination(pagination);

        return dto;
    }
    public List<Map<String, Object>> getPostsSortedByUpdatedAt() {
        List<BlogArticle> articles = blogArticleRepository.findAll();

        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);

        int currentYear = LocalDate.now().getYear();

        // ✅ Sort articles by updatedAt descending (latest first)
        articles.sort((a1, a2) -> {
            LocalDateTime date1 = parseDate(a1.getUpdatedAt(), isoFormatter, customFormatter, currentYear);
            LocalDateTime date2 = parseDate(a2.getUpdatedAt(), isoFormatter, customFormatter, currentYear);
            return date2.compareTo(date1); // descending
        });

        // ✅ Map to desired JSON structure and limit to 4 latest posts
        return articles.stream()
                .limit(4)
                .map(article -> {
                    Map<String, Object> post = new HashMap<>();
                    post.put("id", article.getId());
                    post.put("cardHeading", article.getCategory());
                    post.put("textHeading", article.getTitle());
                    post.put("text", article.getContent().length() > 200 ? article.getContent().substring(0, 200) + "..." : article.getContent());
                    post.put("image", article.getHeaderImage());
                    return post;
                })
                .collect(Collectors.toList());
    }

    private LocalDateTime parseDate(String updatedAt, DateTimeFormatter isoFormatter, DateTimeFormatter customFormatter, int currentYear) {
        if (updatedAt == null || updatedAt.isEmpty()) {
            return LocalDateTime.MIN;
        }
        try {
            return LocalDateTime.parse(updatedAt, isoFormatter);
        } catch (Exception e) {
            try {
                updatedAt = updatedAt.replaceAll("(st|nd|rd|th)", "") + " " + currentYear;
                LocalDate parsedDate = LocalDate.parse(updatedAt.trim(), customFormatter);
                return parsedDate.atStartOfDay();
            } catch (Exception ex) {
                System.out.println("Failed to parse date: " + updatedAt);
                return LocalDateTime.MIN;
            }
        }
    }



    public BlogCommentDTO addComment(Long articleId, BlogCommentDTO commentDTO) {
        BlogArticle article = blogArticleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + articleId));

        BlogComment comment = new BlogComment();
        comment.setName(commentDTO.getName());
        comment.setDate(commentDTO.getDate());
        comment.setText(commentDTO.getText());
        comment.setAvatar(commentDTO.getAvatar());
        comment.setArticle(article);

        BlogComment savedComment = blogCommentRepository.save(comment);

        // Convert savedComment to BlogCommentDTO to return
        BlogCommentDTO savedDTO = new BlogCommentDTO();
        savedDTO.setName(savedComment.getName());
        savedDTO.setDate(savedComment.getDate());
        savedDTO.setText(savedComment.getText());
        savedDTO.setAvatar(savedComment.getAvatar());
        savedDTO.setArticleId(articleId);

        return savedDTO;
    }
    public BlogDTO addBlog(BlogDTO blogDTO) {
        BlogArticle article = new BlogArticle();

        // Basic fields
        article.setTitle(blogDTO.getArticleData().getTitle());
        article.setCategory(blogDTO.getArticleData().getCategory());
        article.setContent(blogDTO.getArticleData().getContent());
        article.setHeaderImage(blogDTO.getArticleData().getHeaderImage());
        article.setUpdatedAt(blogDTO.getArticleData().getUpdatedAt());
        article.setReadTime(blogDTO.getArticleData().getReadTime());

        // Quote
        if (blogDTO.getArticleData().getQuote() != null) {
            article.setQuoteText(blogDTO.getArticleData().getQuote().getText());
            article.setQuoteAuthor(blogDTO.getArticleData().getQuote().getAuthor());
        }

        // Categories
        if (blogDTO.getCategories() != null) {
            List<BlogCategory> categories = blogDTO.getCategories().stream()
                    .map(cat -> {
                        BlogCategory c = new BlogCategory();
                        c.setCategory(cat);
                        c.setArticle(article);
                        return c;
                    }).collect(Collectors.toList());
            article.setCategories(categories);
        }

        // Tags
        if (blogDTO.getTags() != null) {
            List<BlogTag> tags = blogDTO.getTags().stream()
                    .map(tag -> {
                        BlogTag t = new BlogTag();
                        t.setTag(tag);
                        t.setArticle(article);
                        return t;
                    }).collect(Collectors.toList());
            article.setTags(tags);
        }

        // Social Links
        if (blogDTO.getSocialLinks() != null) {
            List<BlogSocialLink> socialLinks = blogDTO.getSocialLinks().stream()
                    .map(link -> {
                        BlogSocialLink sl = new BlogSocialLink();
                        sl.setLink(link);
                        sl.setArticle(article);
                        return sl;
                    }).collect(Collectors.toList());
            article.setSocialLinks(socialLinks);
        }

        // Gadgets
        if (blogDTO.getArticleData().getGadgetsList() != null) {
            List<BlogGadget> gadgets = blogDTO.getArticleData().getGadgetsList().stream()
                    .map(g -> {
                        BlogGadget gadget = new BlogGadget();
                        gadget.setGadget(g);
                        gadget.setArticle(article);
                        return gadget;
                    }).collect(Collectors.toList());
            article.setGadgets(gadgets);
        }

        // Bottom Paragraphs
        if (blogDTO.getArticleData().getBottomParagraphs() != null) {
            List<BlogBottomParagraph> bottomParas = blogDTO.getArticleData().getBottomParagraphs().stream()
                    .map(p -> {
                        BlogBottomParagraph para = new BlogBottomParagraph();
                        para.setParagraph(p);
                        para.setArticle(article);
                        return para;
                    }).collect(Collectors.toList());
            article.setBottomParagraphs(bottomParas);
        }
        if (blogDTO.getComments() != null) {
            List<BlogComment> comments = blogDTO.getComments().stream()
                    .map(c -> {
                        BlogComment comment = new BlogComment();
                        comment.setName(c.getName());
                        comment.setDate(c.getDate());
                        comment.setText(c.getText());
                        comment.setAvatar(c.getAvatar());
                        comment.setArticle(article);
                        return comment;
                    }).collect(Collectors.toList());
            article.setComments(comments);
        }


        // Responsive Section
        if (blogDTO.getArticleData().getResponsiveSection() != null) {
            BlogDTO.ResponsiveSection rsDTO = blogDTO.getArticleData().getResponsiveSection();
            BlogResponsiveSection rs = new BlogResponsiveSection();
            rs.setTitle(rsDTO.getTitle());
            rs.setImage(rsDTO.getImage());
            rs.setArticle(article);

            if (rsDTO.getParagraphs() != null) {
                List<BlogResponsiveParagraph> paragraphs = rsDTO.getParagraphs().stream()
                        .map(p -> {
                            BlogResponsiveParagraph para = new BlogResponsiveParagraph();
                            para.setParagraph(p);
                            para.setResponsiveSection(rs);
                            return para;
                        }).collect(Collectors.toList());
                rs.setParagraphs(paragraphs);
            }

            article.setResponsiveSections(List.of(rs));
        }

        // Save article
        BlogArticle savedArticle = blogArticleRepository.save(article);

        // Convert back to BlogDTO (you can reuse your getBlogById for full populated object)
        return getBlogById(savedArticle.getId());
    }



}
