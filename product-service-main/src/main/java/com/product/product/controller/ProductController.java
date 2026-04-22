package com.product.product.controller;


import com.product.product.DTO.ProductRequest;
import com.product.product.DTO.ProductResponse;
import com.product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ProductResponse> createProduct (@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.createRequest(productRequest),
                HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct
            (@PathVariable Long id,
             @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    @GetMapping("/search")
    public  ResponseEntity<List<ProductResponse>>  searchProduct(@RequestParam String keyword) {
    return ResponseEntity.ok(productService.searchProduct(keyword));
    }

}
