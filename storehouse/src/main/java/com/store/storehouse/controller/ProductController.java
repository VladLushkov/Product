package com.store.storehouse.controller;

import com.store.storehouse.dto.ProductDto;
import com.store.storehouse.dto.ProductTypeDto;
import com.store.storehouse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<HttpStatus> createProduct(@RequestBody ProductDto productDto) {
        productService.save(productDto);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping("/add-products")
    public ResponseEntity<HttpStatus> addProducts(@RequestBody ProductDto productDto) {
        productService.addProducts(productDto);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/types")
    public ResponseEntity<List<ProductTypeDto>> getProductTypes() {
        return ResponseEntity.ok(productService.getProductTypes());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }
}
