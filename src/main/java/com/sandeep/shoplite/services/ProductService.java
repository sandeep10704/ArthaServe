package com.sandeep.shoplite.services;

import com.sandeep.shoplite.dto.ProductPageResponseDTO;
import com.sandeep.shoplite.dto.ProductRequestDTO;
import com.sandeep.shoplite.dto.ProductResponseDTO;
import com.sandeep.shoplite.dto.ProductReviewDTO;
import com.sandeep.shoplite.entity.*;
import com.sandeep.shoplite.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository ReviewRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ColorRepository colorRepository;

    @Autowired private SizeRepository sizeRepository;



    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    private ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        System.out.println("Product: " + product.getName() + " (ID: " + product.getId() + ")");
        dto.setId(product.getId());

        // --- Product Details Mapping ---
        ProductResponseDTO.ProductDetails details = new ProductResponseDTO.ProductDetails();
        details.setName(product.getName());
        details.setPrice(product.getPrice());
        details.setRating(product.getRating());
        details.setDescription(product.getDescription());
        details.setStock(product.getStock());
        details.setSku(product.getSku());
        details.setBrand(product.getBrand());

        details.setColors(product.getColors() == null
                ? null
                : product.getColors().stream().map(Color::getName).collect(Collectors.toSet()));

        details.setSizes(product.getSizes() == null
                ? null
                : product.getSizes().stream().map(Size::getName).collect(Collectors.toSet()));

        details.setCategory(product.getCategories() == null
                ? null
                : product.getCategories().stream().map(Category::getName).collect(Collectors.toSet()));

        details.setTags(product.getTags() == null
                ? null
                : product.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

        if (product.getImages() == null || product.getImages().isEmpty()) {
            System.out.println("❌ Images are NULL or EMPTY for product ID: " + product.getId());
        } else {
            System.out.println("✅ Found " + product.getImages().size() + " images for product ID: " + product.getId());
        }

        details.setImages(product.getImages() == null
                ? null
                : product.getImages().stream().map(ProductImage::getImageUrl).collect(Collectors.toSet()));

        dto.setProduct(details);

        // --- Product Description Mapping ---
        ProductDescription description = product.getProductDescription();
        if (description == null) {
            System.out.println("❌ productDescription is NULL for product ID: " + product.getId());
        } else {
            System.out.println("✅ productDescription loaded for product ID: " + product.getId() +
                    ", Title: " + description.getTitle());

            ProductResponseDTO.ProductDescription descriptionDTO = new ProductResponseDTO.ProductDescription();
            descriptionDTO.setTitle(description.getTitle());
            descriptionDTO.setTopText(description.getTopText());
            descriptionDTO.setBottomText(description.getBottomText());
            System.out.println(">>> topText: " + description.getTopText());
            System.out.println(">>> bottomText: " + description.getBottomText());


            if (description.getPoints() == null || description.getPoints().isEmpty()) {
                System.out.println("⚠️ No description points for product ID: " + product.getId());
            } else {
                System.out.println("✅ Found " + description.getPoints().size() + " description points.");
            }

            descriptionDTO.setPoints(description.getPoints() == null
                    ? null
                    : description.getPoints().stream().map(ProductDescriptionPoint::getPoint).collect(Collectors.toSet()));

            dto.setProductDescription(descriptionDTO);
        }

        // --- Reviews Mapping ---
        if (product.getReviews() != null) {
            System.out.println("✅ Found " + product.getReviews().size() + " reviews.");
            Set<ProductResponseDTO.ProductReview> reviews = product.getReviews().stream().map(review -> {
                ProductResponseDTO.ProductReview reviewDTO = new ProductResponseDTO.ProductReview();
                reviewDTO.setName(review.getReviewerName());
                reviewDTO.setDate(review.getReviewDate().toString());
                reviewDTO.setText(review.getText());
                reviewDTO.setImage(review.getImageUrl());
                return reviewDTO;
            }).collect(Collectors.toSet());

            dto.setReviews(reviews);
        } else {
            System.out.println("⚠️ Reviews are NULL for product ID: " + product.getId());
        }

        return dto;
    }
    private ProductPageResponseDTO.FilterSection createFilter(String header, List<String> topics) {
        ProductPageResponseDTO.FilterSection filter = new ProductPageResponseDTO.FilterSection();
        filter.setHeader(header);
        filter.setTopics(topics);
        return filter;
    }

    public ProductPageResponseDTO getProductPage() {
        List<Product> products = productRepository.findAll();

        // --- Product Cards ---
        List<ProductPageResponseDTO.ProductCard> productCards = products.stream().map(product -> {
            ProductPageResponseDTO.ProductCard card = new ProductPageResponseDTO.ProductCard();
            card.setId(product.getId());
            card.setText(product.getName());
            card.setAmount("$" + product.getPrice());

            card.setTags(product.getTags().stream().map(Tag::getName).toList());
            card.setCategory(product.getCategories().stream().findFirst().map(Category::getName).orElse("Unknown"));
            card.setBrand(product.getBrand());
            card.setImage(product.getImages().stream().findFirst().map(ProductImage::getImageUrl).orElse(""));

            double price = product.getPrice();
            if (price < 10) card.setPriceFilter("Less than $10");
            else if (price < 20) card.setPriceFilter("$10- $20");
            else if (price < 30) card.setPriceFilter("$20- $30");
            else if (price < 40) card.setPriceFilter("$30- $40");
            else if (price < 50) card.setPriceFilter("$40- $50");
            else card.setPriceFilter("Above $50");

            return card;
        }).toList();

        // --- Filter Sections (dynamically) ---
        Set<String> categories = products.stream()
                .flatMap(p -> p.getCategories().stream())
                .map(Category::getName)
                .collect(Collectors.toSet());

        Set<String> tags = products.stream()
                .flatMap(p -> p.getTags().stream())
                .map(Tag::getName)
                .collect(Collectors.toSet());

        Set<String> brands = products.stream()
                .map(Product::getBrand)
                .filter(b -> b != null && !b.isBlank())
                .collect(Collectors.toSet());

        List<ProductPageResponseDTO.FilterSection> filters = List.of(
                createFilter("Categories", new ArrayList<>(categories)),
                createFilter("Tags", new ArrayList<>(tags)),
                createFilter("Brands", new ArrayList<>(brands)),
                createFilter("Filter by Price", List.of(
                        "Less than $10", "$10- $20", "$20- $30", "$30- $40", "$40- $50", "Above $50"
                ))
        );

        // --- Pagination ---
        int total = productCards.size();
        int itemsPerPage = 12;
        int pageCount = (int) Math.ceil((double) total / itemsPerPage);

        ProductPageResponseDTO.Pagination pagination = new ProductPageResponseDTO.Pagination();
        pagination.setItemsPerPage(itemsPerPage);
        pagination.setTotalProducts(total);
        pagination.setPageCount(pageCount);

        // --- Final Response ---
        ProductPageResponseDTO response = new ProductPageResponseDTO();
        response.setPage("MainShopLayout");
        response.setFilters(filters);
        response.setProducts(productCards);
        response.setPagination(pagination);

        return response;
    }
    public ProductReviewDTO addReview(Long productId, ProductReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Review review = new Review();
        review.setReviewerName(reviewDTO.getReviewerName());
        review.setReviewDate(java.time.LocalDate.parse(reviewDTO.getReviewDate()));
        review.setText(reviewDTO.getText());
        review.setImageUrl(reviewDTO.getImageUrl());
        review.setProduct(product);

        Review savedReview = ReviewRepository.save(review);

        ProductReviewDTO savedDTO = new ProductReviewDTO();
        savedDTO.setReviewerName(savedReview.getReviewerName());
        savedDTO.setReviewDate(savedReview.getReviewDate().toString());
        savedDTO.setText(savedReview.getText());
        savedDTO.setImageUrl(savedReview.getImageUrl());

        return savedDTO;
    }
    public ProductResponseDTO addFullProduct(ProductRequestDTO request) {
        ProductResponseDTO.ProductDetails details = request.getProduct();
        ProductResponseDTO.ProductDescription desc = request.getProductDescription();
        List<ProductResponseDTO.ProductReview> reviews = request.getReviews();

        // Step 1: Create Product entity
        Product product = new Product();
        product.setName(details.getName());
        product.setPrice(details.getPrice());
        product.setRating(details.getRating());
        product.setDescription(details.getDescription());
        product.setStock(details.getStock());
        product.setSku(details.getSku());
        product.setBrand(details.getBrand());

        // Step 2: Set colors
        if (details.getColors() != null) {
            Set<Color> colors = details.getColors().stream()
                    .map(name -> colorRepository.findByName(name)
                            .orElseGet(() -> colorRepository.save(new Color(name))))
                    .collect(Collectors.toSet());
            product.setColors(colors);
        }

        // Step 3: Set sizes
        if (details.getSizes() != null) {
            Set<Size> sizes = details.getSizes().stream()
                    .map(name -> sizeRepository.findByName(name)
                            .orElseGet(() -> sizeRepository.save(new Size(name))))
                    .collect(Collectors.toSet());
            product.setSizes(sizes);
        }

        // Step 4: Set categories
        if (details.getCategory() != null) {
            Set<Category> categories = details.getCategory().stream()
                    .map(name -> categoryRepository.findByName(name)
                            .orElseGet(() -> categoryRepository.save(new Category(name))))
                    .collect(Collectors.toSet());
            product.setCategories(categories);
        }

        // Step 5: Set tags
        if (details.getTags() != null) {
            Set<Tag> tags = details.getTags().stream()
                    .map(name -> tagRepository.findByName(name)
                            .orElseGet(() -> tagRepository.save(new Tag(name))))
                    .collect(Collectors.toSet());
            product.setTags(tags);
        }

        // Step 6: Set images
        if (details.getImages() != null) {
            Set<ProductImage> images = details.getImages().stream()
                    .map(url -> {
                        ProductImage image = new ProductImage();
                        image.setImageUrl(url);
                        image.setProduct(product); // associate with product
                        return image;
                    }).collect(Collectors.toSet());
            product.setImages(images);
        }

        // Step 7: Create and set product description
        if (desc != null) {
            ProductDescription description = new ProductDescription();
            description.setTitle(desc.getTitle());
            description.setTopText(desc.getTopText());
            description.setBottomText(desc.getBottomText());
            description.setProduct(product); // associate with product

            if (desc.getPoints() != null) {
                List<ProductDescriptionPoint> points = desc.getPoints().stream()
                        .map(p -> {
                            ProductDescriptionPoint point = new ProductDescriptionPoint();
                            point.setPoint(p);
                            point.setProductDescription(description);
                            return point;
                        }).collect(Collectors.toList()); // ✅ collect to List

                description.setPoints(points);

            }

            product.setProductDescription(description);
        }

        // Step 8: Save product (cascades will save related entities)
        Product savedProduct = productRepository.save(product);

        // Step 9: Add reviews
        if (reviews != null) {
            for (ProductResponseDTO.ProductReview reviewDTO : reviews) {
                Review review = new Review();
                review.setReviewerName(reviewDTO.getName());
                review.setReviewDate(LocalDate.parse(reviewDTO.getDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                review.setText(reviewDTO.getText());
                review.setImageUrl(reviewDTO.getImage());
                review.setProduct(savedProduct);
                reviewRepository.save(review);
            }
        }

        return getProductById(savedProduct.getId());
    }



}
