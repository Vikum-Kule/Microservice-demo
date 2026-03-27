package microservice.product_service.service;

import microservice.product_service.entity.Product;
import microservice.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Product> getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> getSelectedProducts(List<Long> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty() || productIds.size() != products.size()) {

        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<Double> getProductsTotalPrice(List<Long> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty() || productIds.size() != products.size()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Double totalPrice = 0.0;
        for(Product product : products){
            totalPrice += product.getProductPrice();
        }
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }
}
