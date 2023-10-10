package com.plantcare.serverapplication.ordermanagement.product;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto convertToDto(Product product);
    Product convertToEntity(ProductDto productDto);
}
