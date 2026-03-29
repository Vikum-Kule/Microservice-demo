package microservice.product_service.controller;

import microservice.product_service.entity.Product;
import microservice.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    Environment environment;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        System.out.println(environment.getProperty("local.server.port"));
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
