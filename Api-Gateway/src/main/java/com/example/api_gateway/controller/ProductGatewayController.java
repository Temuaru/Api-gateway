package com.example.api_gateway.controller;

import com.example.api_gateway.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/products")
public class ProductGatewayController {

    private final RestTemplate restTemplate;
    private final String coreServiceUrl;

    public ProductGatewayController(RestTemplate restTemplate,
                                    @Value("${remote.core}") String coreServiceUrl) {
        this.restTemplate = restTemplate;
        this.coreServiceUrl = coreServiceUrl;
    }

    // GET /products
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        String url = coreServiceUrl + "/products";
        return restTemplate.getForEntity(url, Object.class);
    }

    // GET /products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        String url = coreServiceUrl + "/products/" + id;
        return restTemplate.getForEntity(url, Object.class);
    }

    // POST /products
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        String url = coreServiceUrl + "/products";
        return restTemplate.postForEntity(url, product, Object.class);
    }

    // PUT /products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestBody Product product) {
        String url = coreServiceUrl + "/products/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Object.class);
    }

    // DELETE /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        String url = coreServiceUrl + "/products/" + id;
        restTemplate.delete(url);
        return ResponseEntity.noContent().build();
    }
}