package microservice.product_service.controller;

import microservice.product_service.entity.Product;
import microservice.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/find-products")
    public ResponseEntity<List<Product>> findSelectedProducts(@RequestParam List<Long> productIds) {
        return productService.getSelectedProducts(productIds);
    }

    @GetMapping("/get-total")
    public ResponseEntity<Double> getProductsTotalPrice(@RequestParam List<Long> productIds) {
        return productService.getProductsTotalPrice(productIds);
    }
}
