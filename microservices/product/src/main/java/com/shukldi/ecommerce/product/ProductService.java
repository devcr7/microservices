package com.shukldi.ecommerce.product;

import com.shukldi.ecommerce.handler.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(@Valid ProductRequest request) {
        return productRepository.save(productMapper.toProduct(request)).getId();
    }

    public List<PurchaseResponse> purchaseProduct(List<PurchaseRequest> request) {
        List<Integer> productIds = request.stream()
                .map(PurchaseRequest::productId)
                .toList();
        List<Product> products = productRepository.findAllById(productIds);

        if(products.size() != productIds.size()) {
            throw new ResourceNotFoundException("Some products not found");
        }

        products.sort(Comparator.comparingInt(Product::getId));
        List<PurchaseResponse> purchaseResponses = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            PurchaseRequest purchaseRequest = request.get(i);
            if (product.getAvailableQuantity() < purchaseRequest.quantity()) {
                throw new ResourceNotFoundException("Product with id " + product.getId() + " not enough quantity");
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - purchaseRequest.quantity());
            productRepository.save(product);
            purchaseResponses.add(productMapper.toPurchaseResponse(product, purchaseRequest.quantity()));
        }

        return purchaseResponses;
    }

    public ProductResponse findProductById(Integer id) {
        return productRepository.findById(id)
                .map(ProductMapper::toProductResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }
}
