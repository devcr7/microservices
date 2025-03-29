package com.shukldi.ecommerce.product;

import com.shukldi.ecommerce.category.Category;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }
    public static Product toProduct(ProductRequest request) {
        return new Product(
                request.id(),
                request.name(),
                request.description(),
                request.availableQuantity(),
                request.price(),
                Category.builder()
                        .id(request.id())
                        .build()
        );
    }

    public PurchaseResponse toPurchaseResponse(Product product, @NotNull(message = "Quantity is mandatory") double quantity) {
        return new PurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailableQuantity()
        );
    }
}
