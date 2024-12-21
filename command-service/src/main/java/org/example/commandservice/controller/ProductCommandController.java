package org.example.commandservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.commandservice.service.ProductService;
import org.example.sharedlib.model.Product;
import org.example.sharedlib.model.ProductEvent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody ProductEvent productEvent) {
        return productService.createProduct(productEvent);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductEvent productEvent) {
        return productService.updateProduct(id, productEvent);
    }

}
