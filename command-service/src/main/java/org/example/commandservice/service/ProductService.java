package org.example.commandservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.sharedlib.model.EventType;
import org.example.sharedlib.model.Product;
import org.example.sharedlib.model.ProductEvent;
import org.example.sharedlib.repository.ProductRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public Product createProduct(ProductEvent productEvent) {
        Product product = productRepository.save(productEvent.getProduct());
        try {

            ProductEvent event = ProductEvent
                    .builder()
                    .product(product)
                    .eventType(EventType.PRODUCT_CREATED)
                    .build();
            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("product-event-topic", json);
        } catch (Exception e) {
            throw new RuntimeException("Error creating product", e);
        }
        return product;
    }

    public Product updateProduct(Long id, ProductEvent productEvent) {
        Product updatedProduct;
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            Product newProduct = productEvent.getProduct();
            existingProduct.setName(newProduct.getName());
            existingProduct.setPrice(newProduct.getPrice());
            existingProduct.setDescription(newProduct.getDescription());
            updatedProduct = productRepository.save(existingProduct);
            ProductEvent event = ProductEvent.builder()
                    .product(updatedProduct)
                    .eventType(EventType.PRODUCT_UPDATED)
                    .build();
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("product-event-topic", json);
        } catch (Exception e) {
            throw new RuntimeException("Error updating product", e);
        }

        return updatedProduct;
    }
}
