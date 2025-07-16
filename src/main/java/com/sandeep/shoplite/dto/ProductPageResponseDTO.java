package com.sandeep.shoplite.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
public class ProductPageResponseDTO {

    private String page;
    private List<FilterSection> filters;
    private List<ProductCard> products;
    private Pagination pagination;

    @Getter
    @Setter
    public static class FilterSection {
        private String header;
        private List<String> topics;


    }
    @Getter
    @Setter
    public static class ProductCard {
        private Long id;
        private String image;
        private String text;
        private String amount;
        private List<String> tags;
        private String category;
        private String brand;
        private String priceFilter;


    }
    @Getter
    @Setter
    public static class Pagination {
        private int itemsPerPage;
        private int totalProducts;
        private int pageCount;


    }


}
