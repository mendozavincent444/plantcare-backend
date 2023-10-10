package com.plantcare.serverapplication.ordermanagement.product;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
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

    public ProductDto convertToDto(Product product) {
        return ProductDto
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public Product convertToEntity(ProductDto productDto) {
        return Product
                .builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .unitPrice(productDto.getUnitPrice())
                .imageUrl(productDto.getImageUrl())
                .build();
    }
}
