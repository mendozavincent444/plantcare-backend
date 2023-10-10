package com.plantcare.serverapplication.ordermanagement.product;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {

        List<Product> products = this.productRepository.findAll();

        return products.stream().map(product -> this.convertToDto(product)).toList();
    }

    private ProductDto convertToDto(Product product) {
        return ProductDto
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
