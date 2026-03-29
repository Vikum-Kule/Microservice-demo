package microservice.order_service.service;

import microservice.order_service.dto.CreateOrderDTO;
import microservice.order_service.dto.CustomerDTO;
import microservice.order_service.dto.OrderDetailsDTO;
import microservice.order_service.dto.ProductDTO;
import microservice.order_service.entity.Order;
import microservice.order_service.feign.CustomerInterface;
import microservice.order_service.feign.ProductInterface;
import microservice.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerInterface customerInterface;

    @Autowired
    private ProductInterface productInterface;

    public ResponseEntity<Order> createOrder(CreateOrderDTO createOrderDTO) {
        //check customer whether exist
        CustomerDTO customerDTO = customerInterface.getCustomerById(createOrderDTO.getCustomerId()).getBody();
        if (customerDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //check products whether exist
        for (Long productId : createOrderDTO.getProductIds()) {
            ProductDTO productDTO = productInterface.getProductById(productId).getBody();
            if (productDTO == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        //total calculation
        Double totalProductPrice = productInterface.getProductsTotalPrice(createOrderDTO.getProductIds()).getBody();
        if (totalProductPrice == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //order placing
        LocalDate deliveryDate = calculateDeliveryDate();
        Order order = Order.builder()
                           .productIds(createOrderDTO.getProductIds())
                           .customerId(createOrderDTO.getCustomerId())
                           .deliveryDate(deliveryDate)
                           .total(totalProductPrice)
                           .build();
        return new ResponseEntity<>(orderRepository.save(order), HttpStatus.CREATED);

    }

    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<Order> getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    public ResponseEntity<OrderDetailsDTO> getOrderDetailsById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            // get customer details
            CustomerDTO customerDTO = customerInterface.getCustomerById(order.getCustomerId()).getBody();

            // get order products
            List<ProductDTO> productList = new ArrayList<>();
            for (Long productId : order.getProductIds()) {
                ProductDTO productDTO = productInterface.getProductById(productId).getBody();
                productList.add(productDTO);
            }
            // assign order details
            OrderDetailsDTO orderDetails = OrderDetailsDTO.builder()
                                                          .orderId(order.getOrderId())
                                                          .orderDate(order.getOrderDate())
                                                          .deliveryDate(order.getDeliveryDate())
                                                          .customer(customerDTO)
                                                          .products(productList).total(order.getTotal())
                                                          .build();
            return new ResponseEntity<>(orderDetails, HttpStatus.OK);

        }
    }

    private LocalDate calculateDeliveryDate() {
        return LocalDate.now()
                        .plusMonths(1)
                        .withDayOfMonth(1);
    }
}
