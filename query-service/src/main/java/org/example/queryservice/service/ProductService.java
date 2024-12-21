package org.example.queryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.sharedlib.model.Product;
import org.example.sharedlib.model.ProductEvent;
import org.example.sharedlib.repository.ProductRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @KafkaListener(topics = "product-event-topic", groupId = "product-event-group")
    public void processProductEvents(String json) {
        try{
            ProductEvent productEvent = objectMapper.readValue(json, ProductEvent.class);
            Product product = productEvent.getProduct();
            switch (productEvent.getEventType()) {
                case PRODUCT_CREATED:
                    product.setId(null);
                    productRepository.save(product);
                    break;
                case PRODUCT_UPDATED:
                    Product existingProduct = productRepository.findByCode(product.getCode())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    productRepository.save(existingProduct);
            }
        }catch (Exception e){
           throw new RuntimeException(e);
        }

    }
}
